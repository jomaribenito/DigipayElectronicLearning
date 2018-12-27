package ph.digipay.digipayelectroniclearning.ui.admin.pdf;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Optional;
import com.mobsandgeeks.saripaar.annotation.Url;

import java.util.ArrayList;
import java.util.List;

import ph.digipay.digipayelectroniclearning.BuildConfig;
import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.FormUtils;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.ui.common.firebase_db.FirebaseDatabaseHelper;

public class PDFFormActivity extends BaseActivity implements Validator.ValidationListener {

    private Spinner moduleSpnr;
    @Url
    protected TextInputEditText pdfUrlTiet;
    @NotEmpty
    protected TextInputEditText pdfNameTiet;
    @Optional
    protected TextInputEditText pdfDescriptionTiet;

    private Validator validator;

    private FirebaseDatabaseHelper<PDFForm> pdfFormFirebaseDatabase;
    private List<Module> moduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfform);

        moduleSpnr = findViewById(R.id.module_spnr);
        pdfUrlTiet = findViewById(R.id.pdf_url_tiet);
        pdfNameTiet = findViewById(R.id.pdf_name_tiet);
        pdfDescriptionTiet = findViewById(R.id.pdf_description_tiet);

        validator = new Validator(this);
        validator.setValidationListener(this);

        pdfFormFirebaseDatabase = new FirebaseDatabaseHelper<>(PDFForm.class);

        if (BuildConfig.DEBUG){
            pdfUrlTiet.setText("https://s3-ap-southeast-1.amazonaws.com/digipay-core-bucket/production/docs/FSG+Data+Privacy+Statement_July+2018.pdf");
        }

        FirebaseDatabaseHelper<Module> moduleFirebaseDatabase = new FirebaseDatabaseHelper<>(Module.class);
        moduleFirebaseDatabase.fetchItems(StringConstants.MODULE_DB, itemList -> {
            moduleList = itemList;
            List<String> moduleNameList = new ArrayList<>();
            for (Module module : itemList) {
                moduleNameList.add(module.getName());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, moduleNameList);
            moduleSpnr.setAdapter(arrayAdapter);
        });


        findViewById(R.id.add_pdf_btn).setOnClickListener(v -> validator.validate());
    }

    @Override
    public void onValidationSucceeded() {
        String moduleUid = getModuleUid(FormUtils.getSpinnerItemString(moduleSpnr));
        createNewPDF(moduleUid,
                FormUtils.getTrimmedString(pdfUrlTiet),
                FormUtils.getTrimmedString(pdfNameTiet),
                FormUtils.getTrimmedString(pdfDescriptionTiet));
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        displayErrors(errors);
    }

    private void createNewPDF(String moduleUid, String pdfUrl, String pdfName, String pdfDescription) {
        PDFForm pdfForm = new PDFForm();
        pdfForm.setModuleUid(moduleUid);
        pdfForm.setPdfLink(pdfUrl);
        pdfForm.setName(pdfName);
        pdfForm.setDescription(pdfDescription);
        pdfFormFirebaseDatabase.insertItems(StringConstants.PDF_LIST_DB, pdfForm);
    }

    private String getModuleUid(String moduleName){
        String moduleUid = null;
        for (Module module : moduleList) {
            if (module.getName().equals(moduleName)) {
                moduleUid = module.getUid();
            }
        }
        return moduleUid;
    }
}
