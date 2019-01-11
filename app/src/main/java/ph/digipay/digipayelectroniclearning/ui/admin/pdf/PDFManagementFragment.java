package ph.digipay.digipayelectroniclearning.ui.admin.pdf;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseFragment;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.common.utils.SwipeHelper;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.ui.common.firebase_db.FirebaseDatabaseHelper;


public class PDFManagementFragment extends BaseFragment {

    private RecyclerView pdfRv;
    private FloatingActionButton fab;
    private PDFListRecyclerAdapter pdfListRecyclerAdapter;

    private FirebaseDatabaseHelper<PDFForm> pdfFormFirebaseDatabase;
    private List<PDFForm> pdfFormList;

    public PDFManagementFragment() {
        // Required empty public constructor
    }

    public static PDFManagementFragment newInstance() {
        PDFManagementFragment fragment = new PDFManagementFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initialize() {
        pdfFormFirebaseDatabase = new FirebaseDatabaseHelper<>(PDFForm.class);

        pdfFormFirebaseDatabase.fetchItems(StringConstants.PDF_LIST_DB, itemList -> {
            pdfFormList = itemList;
            pdfListRecyclerAdapter = new PDFListRecyclerAdapter(itemList);
            pdfRv.setLayoutManager(new EndlessRecyclerLinearLayoutManager(getBaseActivity().getBaseContext()));
            pdfRv.setAdapter(pdfListRecyclerAdapter);
        });

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseActivity().getBaseContext());
        new SwipeHelper(getBaseActivity().getBaseContext(), pdfRv) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        pos -> {
                            final PDFForm pdfForm = pdfFormList.get(pos);
                            alertDialog.setTitle("Delete PDF")
                                    .setMessage("This pdf will be deleted.")
                                    .setCancelable(false)
                                    .setPositiveButton("Delete", (dialog, which) -> {
                                        pdfFormFirebaseDatabase.removeItem(StringConstants.PDF_LIST_DB, pdfForm.getUid());
                                        pdfListRecyclerAdapter.clear();
                                    })
                                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                                    .show();

                        }));
            }
        };


        fab.setOnClickListener(v -> {
            startActivity(new Intent(getBaseActivity().getBaseContext(), PDFFormActivity.class));
            pdfListRecyclerAdapter.clear();
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pdfmanagement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pdfRv = view.findViewById(R.id.pdf_list_rv);
        fab = view.findViewById(R.id.fab);
        super.onViewCreated(view, savedInstanceState);
    }
}
