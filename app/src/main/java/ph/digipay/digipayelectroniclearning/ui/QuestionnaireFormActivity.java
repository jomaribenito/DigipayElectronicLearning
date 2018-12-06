package ph.digipay.digipayelectroniclearning.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.FormUtils;
import ph.digipay.digipayelectroniclearning.models.Options;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;

public class QuestionnaireFormActivity extends BaseActivity implements Validator.ValidationListener {

    @NotEmpty
    private TextInputEditText questionTiet;

    @NotEmpty
    private TextInputEditText option1Tiet;

    @NotEmpty
    private TextInputEditText option2Tiet;

    @NotEmpty
    private TextInputEditText option3Tiet;

    @Checked(message = "Please select an answer")
    private RadioGroup optionsRg;

    private Validator validator;

    private FirebaseDatabase mDatabase;
    private DatabaseReference questionnaireDataReference;

    private String uid = null;
    private boolean isAdding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_form);

        questionTiet = findViewById(R.id.question_et);
        option1Tiet = findViewById(R.id.option1_tiet);
        option2Tiet = findViewById(R.id.option2_tiet);
        option3Tiet = findViewById(R.id.option3_tiet);
        optionsRg = findViewById(R.id.options_rg);
        Button actionBtn = findViewById(R.id.action_btn);

        validator = new Validator(this);
        validator.setValidationListener(this);

        mDatabase = FirebaseDatabase.getInstance();
        questionnaireDataReference = mDatabase.getReference("questionnaire");


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            actionBtn.setText(getString(R.string.update_questionnaire_btn));
            Questionnaire questionnaire = bundle.getParcelable(StringConstants.QUESTIONNAIRE_FORMS);
            assert questionnaire != null;
            uid = questionnaire.getId();
            questionTiet.setText(questionnaire.getQuestion());
            option1Tiet.setText(questionnaire.getOptions().getOption1());
            option2Tiet.setText(questionnaire.getOptions().getOption2());
            option3Tiet.setText(questionnaire.getOptions().getOption3());
            ((RadioButton) optionsRg.getChildAt(Integer.parseInt(questionnaire.getAnswer_index()))).setChecked(true);
            isAdding = false;
        } else {
            actionBtn.setText(getString(R.string.add_questionnaire_btn));
            isAdding = true;
        }


        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

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
        if (isAdding) {
            createNewQuestionnaire(FormUtils.getTrimmedString(questionTiet), options, answerIndex);
        } else {
            updateQuestionnaireItem(uid, FormUtils.getTrimmedString(questionTiet), options, answerIndex);
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        displayErrors(errors);
    }

    private void createNewQuestionnaire(String question, String[] options, String answerIndex) {
        String id = questionnaireDataReference.push().getKey();
        assert id != null;
        Questionnaire questionnaire = new Questionnaire(id);
        questionnaire.setQuestion(question);
        questionnaire.setOptions(new Options(options[0], options[1], options[2]));
        questionnaire.setAnswer_index(answerIndex);
        questionnaireDataReference.child(id).setValue(questionnaire);
        showToast(getString(R.string.add_questionnaire_success_msg));
        finish();
    }

    private void updateQuestionnaireItem(String uid, String question, String [] options, String answerIndex){
        questionnaireDataReference.child(uid).child("question").setValue(question);
        questionnaireDataReference.child(uid).child("options").child("option1").setValue(options[0]);
        questionnaireDataReference.child(uid).child("options").child("option2").setValue(options[1]);
        questionnaireDataReference.child(uid).child("options").child("option3").setValue(options[2]);
        questionnaireDataReference.child(uid).child("answer_index").setValue(answerIndex);
        showToast(getString(R.string.update_questionnaire_success_msg));
        finish();
    }

}
