package ph.digipay.digipayelectroniclearning.ui.admin.questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.FormUtils;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.models.Options;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.FirebaseDatabaseHelper;

public class QuestionnaireFormActivity extends BaseActivity implements Validator.ValidationListener {

    @NotEmpty
    protected TextInputEditText questionTiet;
    @NotEmpty
    protected TextInputEditText option1Tiet;
    @NotEmpty
    protected TextInputEditText option2Tiet;
    @NotEmpty
    protected TextInputEditText option3Tiet;
    @Checked(message = "Please select an answer")
    protected RadioGroup optionsRg;
    protected Spinner moduleSpnr;

    private Validator validator;

    private String uid = null;
    private boolean isAdding;

    private FirebaseDatabaseHelper<Questionnaire> questionnaireFirebaseDatabase;
    private List<Module> moduleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_form);

        questionTiet = findViewById(R.id.question_et);
        option1Tiet = findViewById(R.id.option1_tiet);
        option2Tiet = findViewById(R.id.option2_tiet);
        option3Tiet = findViewById(R.id.option3_tiet);
        optionsRg = findViewById(R.id.options_rg);
        moduleSpnr = findViewById(R.id.module_spnr);
        Button actionBtn = findViewById(R.id.action_btn);

        questionnaireFirebaseDatabase = new FirebaseDatabaseHelper<>(Questionnaire.class);
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


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            actionBtn.setText(getString(R.string.update_questionnaire_btn));
            Questionnaire questionnaire = bundle.getParcelable(StringConstants.QUESTIONNAIRE_FORMS);
            assert questionnaire != null;
            questionTiet.setText(questionnaire.getQuestion());
            option1Tiet.setText(questionnaire.getOptions().getOption1());
            option2Tiet.setText(questionnaire.getOptions().getOption2());
            option3Tiet.setText(questionnaire.getOptions().getOption3());
            ((RadioButton) optionsRg.getChildAt(Integer.parseInt(questionnaire.getAnswerIndex()))).setChecked(true);
            isAdding = false;
        } else {
            actionBtn.setText(getString(R.string.add_questionnaire_btn));
            isAdding = true;
        }

        validator = new Validator(this);
        validator.setValidationListener(this);

        actionBtn.setOnClickListener(v -> validator.validate());

    }

    @Override
    public void onValidationSucceeded() {
        String[] options = new String[3];
        options[0] = FormUtils.getTrimmedString(option1Tiet);
        options[1] = FormUtils.getTrimmedString(option2Tiet);
        options[2] = FormUtils.getTrimmedString(option3Tiet);
        int radioButtonID = optionsRg.getCheckedRadioButtonId();
        View radioButton = optionsRg.findViewById(radioButtonID);
        int answer = optionsRg.indexOfChild(radioButton);
        String answerIndex = String.valueOf(answer);
        String moduleUid = getModuleUid(FormUtils.getSpinnerItemString(moduleSpnr));

        if (isAdding) {
            createNewQuestionnaire(moduleUid,
                    FormUtils.getTrimmedString(questionTiet), options, answerIndex);
        } else {
            updateQuestionnaireItem(uid, FormUtils.getTrimmedString(questionTiet), options, answerIndex);
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        displayErrors(errors);
    }

    private void createNewQuestionnaire(String moduleUid, String question, String[] options, String answerIndex) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setModuleUid(moduleUid);
        questionnaire.setQuestion(question);
        questionnaire.setOptions(new Options(options[0], options[1], options[2]));
        questionnaire.setAnswerIndex(answerIndex);
        questionnaireFirebaseDatabase.insertItems(StringConstants.QUESTIONNAIRE_DB, questionnaire);
        showToast(getString(R.string.add_questionnaire_success_msg));
        finish();
    }

    private void updateQuestionnaireItem(String uid, String question, String[] options, String answerIndex) {
        questionnaireFirebaseDatabase.updateItem(StringConstants.QUESTIONNAIRE_DB, uid, "question", null, question);
        questionnaireFirebaseDatabase.updateItem(StringConstants.QUESTIONNAIRE_DB, uid, "options", "option1", options[0]);
        questionnaireFirebaseDatabase.updateItem(StringConstants.QUESTIONNAIRE_DB, uid, "options", "option2", options[1]);
        questionnaireFirebaseDatabase.updateItem(StringConstants.QUESTIONNAIRE_DB, uid, "options", "option3", options[2]);
        questionnaireFirebaseDatabase.updateItem(StringConstants.QUESTIONNAIRE_DB, uid, "answer_index", null, answerIndex);
        showToast(getString(R.string.update_questionnaire_success_msg));
        finish();
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
