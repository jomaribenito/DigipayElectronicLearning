package ph.digipay.digipayelectroniclearning.ui.admin.pdf;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.common.utils.SwipeHelper;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.ui.common.firebase_db.FirebaseDatabaseHelper;

public class PDFManagementActivity extends BaseActivity {

    private RecyclerView pdfRv;
    private PDFListRecyclerAdapter pdfListRecyclerAdapter;

    private FirebaseDatabaseHelper<PDFForm> pdfFormFirebaseDatabase;
    private List<PDFForm> pdfFormList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfmanagement);

        pdfRv = findViewById(R.id.pdf_list_rv);

        pdfFormFirebaseDatabase = new FirebaseDatabaseHelper<>(PDFForm.class);

        pdfFormFirebaseDatabase.fetchItems(StringConstants.PDF_LIST_DB, itemList -> {
            pdfFormList = itemList;
            pdfListRecyclerAdapter = new PDFListRecyclerAdapter(itemList);
            pdfRv.setLayoutManager(new EndlessRecyclerLinearLayoutManager(getApplicationContext()));
            pdfRv.setAdapter(pdfListRecyclerAdapter);
        });

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        new SwipeHelper(this, pdfRv) {
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


        findViewById(R.id.fab).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PDFFormActivity.class));
            pdfListRecyclerAdapter.clear();
        });
    }
}
