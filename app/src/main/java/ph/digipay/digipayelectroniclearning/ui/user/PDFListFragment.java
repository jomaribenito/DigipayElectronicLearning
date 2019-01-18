package ph.digipay.digipayelectroniclearning.ui.user;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseFragment;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.FirebaseDatabaseHelper;
import ph.digipay.digipayelectroniclearning.ui.common.PDFBrowserActivity;

public class PDFListFragment extends BaseFragment {

    private RecyclerView pdfListRv;

    private String moduleUid;

    public PDFListFragment() {
        // Required empty public constructor
    }


    public static PDFListFragment newInstance(String moduleUid) {
        PDFListFragment fragment = new PDFListFragment();
        Bundle args = new Bundle();
        args.putString(StringConstants.MODULE_UID, moduleUid);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initialize() {
        PDFAdapter pdfAdapter = new PDFAdapter();

        getBaseActivity().getDigipayELearningApplication().getAppComponent().getPdfFormFbDatabase()
                .fetchItems(StringConstants.PDF_LIST_DB, itemList -> {
                    pdfAdapter.clear();
                    List<PDFForm> pdfFormList = new ArrayList<>();
                    for (PDFForm pdfForm : itemList) {
                        if (pdfForm.getModuleUid().equals(moduleUid)) {
                            pdfFormList.add(pdfForm);
                        }
                    }
                    pdfAdapter.setItems(pdfFormList);
                });

        pdfAdapter.getPublishSubject().subscribe(pdfForm -> {
            startActivity(new Intent(getBaseActivity().getBaseContext(), PDFBrowserActivity.class).putExtra(StringConstants.PDF_URL, pdfForm.getPdfLink()));
        });

        pdfListRv.setLayoutManager(new EndlessRecyclerLinearLayoutManager(getBaseActivity().getBaseContext()));
        pdfListRv.setAdapter(pdfAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            moduleUid = getArguments().getString(StringConstants.MODULE_UID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pdflist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pdfListRv = view.findViewById(R.id.pdf_list_rv);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
