package ph.digipay.digipayelectroniclearning.ui.admin.video;

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
import java.util.Objects;

import ph.digipay.digipayelectroniclearning.BuildConfig;
import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.FormUtils;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.models.VideoForm;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.FirebaseDatabaseHelper;

public class VideoFormActivity extends BaseActivity implements Validator.ValidationListener {

    private Spinner moduleSpnr;
    @Url
    protected TextInputEditText videoUrlTiet;
    @NotEmpty
    protected TextInputEditText videoNameTiet;
    @Optional
    protected TextInputEditText videoDescriptionTiet;

    private Validator validator;


    private List<Module> moduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_form);


        moduleSpnr = findViewById(R.id.module_spnr);
        videoUrlTiet = findViewById(R.id.video_url_tiet);
        videoNameTiet = findViewById(R.id.video_name_tiet);
        videoDescriptionTiet = findViewById(R.id.video_description_tiet);

        validator = new Validator(this);
        validator.setValidationListener(this);

        if (BuildConfig.DEBUG){
            videoUrlTiet.setText("https://s3-ap-southeast-1.amazonaws.com/healthguard.digipay.ph/tutorial-test.mp4");
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

        findViewById(R.id.add_video_btn).setOnClickListener(v -> validator.validate());

    }

    @Override
    public void onValidationSucceeded() {
        String moduleUid = getModuleUid(FormUtils.getSpinnerItemString(moduleSpnr));
        createNewVideo(moduleUid,
                FormUtils.getTrimmedString(videoUrlTiet),
                FormUtils.getTrimmedString(videoNameTiet),
                FormUtils.getTrimmedString(videoDescriptionTiet));
        finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        displayErrors(errors);
    }

    private void createNewVideo(String moduleUid, String videoUrl, String videoName, String videoDescription) {
        VideoForm videoForm = new VideoForm();
        videoForm.setModuleUid(moduleUid);
        videoForm.setVideoLink(videoUrl);
        videoForm.setName(videoName);
        videoForm.setDescription(videoDescription);
        getDigipayELearningApplication().getAppComponent().getVideoFormFbDatabase()
                .insertItems(StringConstants.VIDEO_LIST_DB, videoForm);
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

    @Override
    public void setUpToolbar() {
        super.setUpToolbar();
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
