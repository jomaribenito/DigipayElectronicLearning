package ph.digipay.digipayelectroniclearning.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.AnswerKey;
import ph.digipay.digipayelectroniclearning.common.constants.QuestionsList;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;

public class QuestionnaireActivity extends BaseActivity {

    private TextSwitcher questionTv;
    private RadioGroup answerRg;
    private RadioButton option1Rb;
    private RadioButton option2Rb;
    private RadioButton option3Rb;
    private Button nextBtn;
    private Button doneBtn;
    private TextView thanksTv;
    private LinearLayout questionnaireContainer;

    List<String> questionsList;
    Map<Integer, List<String>> optionsMap;
    private ArrayList<Integer> answerList;
    private ArrayList<Integer> indexListOfCorrectAnswers;

    int ctr = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        thanksTv = findViewById(R.id.thanks_tv);
        questionTv = findViewById(R.id.question_tv);
        answerRg = findViewById(R.id.answer_rg);
        option1Rb = findViewById(R.id.option1_rb);
        option2Rb = findViewById(R.id.option2_rb);
        option3Rb = findViewById(R.id.option3_rb);

        nextBtn = findViewById(R.id.next_btn);
        doneBtn = findViewById(R.id.done_btn);
        questionnaireContainer = findViewById(R.id.questionnaire_container_ll);

        questionTv.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getApplicationContext());
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                return textView;
            }
        });

        Animation textAnimationIn = AnimationUtils.
                loadAnimation(this, android.R.anim.slide_in_left);
        textAnimationIn.setDuration(800);

        Animation textAnimationOut = AnimationUtils.
                loadAnimation(this, android.R.anim.slide_out_right);
        textAnimationOut.setDuration(800);

        questionTv.setInAnimation(textAnimationIn);
        questionTv.setOutAnimation(textAnimationOut);

        questionsList = QuestionsList.populateQuestions();
        optionsMap = QuestionsList.populateOptions();
        loadQuestions(0);
        defaultData();

        answerList = new ArrayList<>();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerRg.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Answer is required", Toast.LENGTH_SHORT).show();
                } else {
                    int radioButtonID = answerRg.getCheckedRadioButtonId();
                    View radioButton = answerRg.findViewById(radioButtonID);
                    int answer = answerRg.indexOfChild(radioButton);
                    answerList.add(answer);
                    answerRg.clearCheck();
                    if (ctr != questionsList.size()) {
                        loadQuestions(ctr);
                        ctr++;
                    } else {
                        thanksTv.setVisibility(View.VISIBLE);
                        questionnaireContainer.setVisibility(View.GONE);
                        nextBtn.setVisibility(View.GONE);
                        doneBtn.setVisibility(View.VISIBLE);
                    }

                }
            }
        });

        indexListOfCorrectAnswers = new ArrayList<>();
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < answerList.size(); i++) {
                    if (answerList.get(i) == AnswerKey.questionnaireAnswers[i]) {
                        indexListOfCorrectAnswers.add(i);
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList(StringConstants.CORRECT_ANSWERS, indexListOfCorrectAnswers);
                bundle.putIntegerArrayList(StringConstants.ANSWER_LIST, answerList);
                Intent intent = new Intent(getApplicationContext(), EvaluationActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });


    }

    private void loadQuestions(int ctr) {
        questionTv.setText(questionsList.get(ctr));
        option1Rb.setText(optionsMap.get(ctr + 1).get(0));
        option2Rb.setText(optionsMap.get(ctr + 1).get(1));
        if (optionsMap.get(ctr + 1).size() == 2) {
            option3Rb.setVisibility(View.INVISIBLE);
        } else {
            option3Rb.setVisibility(View.VISIBLE);
            option3Rb.setText(optionsMap.get(ctr + 1).get(2));
        }
    }

    private void defaultData() {
        questionTv.setText(questionsList.get(0));
        option1Rb.setText(optionsMap.get(1).get(0));
        option2Rb.setText(optionsMap.get(1).get(1));
        option3Rb.setText(optionsMap.get(1).get(2));
    }
}
