package ph.digipay.digipayelectroniclearning.ui.admin.pdf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.ui.common.firebase_db.FirebaseDatabaseHelper;

public class PDFManagementActivity extends BaseActivity {

    private RecyclerView pdfRv;
    private PDFListRecyclerAdapter pdfListRecyclerAdapter;

    private FirebaseDatabaseHelper<PDFForm> pdfFormFirebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfmanagement);

        pdfRv = findViewById(R.id.pdf_list_rv);

        pdfFormFirebaseDatabase = new FirebaseDatabaseHelper<>(PDFForm.class);

        pdfFormFirebaseDatabase.fetchItems(StringConstants.PDF_LIST_DB, itemList -> {
            pdfListRecyclerAdapter = new PDFListRecyclerAdapter(itemList);
            pdfRv.setLayoutManager(new EndlessRecyclerLinearLayoutManager(getApplicationContext()));
            pdfRv.setAdapter(pdfListRecyclerAdapter);
        });


        findViewById(R.id.fab).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PDFFormActivity.class));
            pdfListRecyclerAdapter.clear();
        });
    }
}
