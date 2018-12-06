package ph.digipay.digipayelectroniclearning.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;

public class QuestionnaireActivity extends BaseActivity {

    private TextSwitcher questionTv;
    private TextSwitcher timerTv;
    private RadioGroup answerRg;
    private RadioButton option1Rb;
    private RadioButton option2Rb;
    private RadioButton option3Rb;
    private Button nextBtn;
    private Button doneBtn;
    private TextView thanksTv;
    private LinearLayout questionnaireContainer;

    private ArrayList<Integer> answerList;

    private FirebaseDatabase mDatabase;
    private DatabaseReference questionnaireDataReference;

    private List<Questionnaire> questionnaireList;

    int ctr = 0;
    private int numberOfCorrectAnswer = 0;

    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        showProgressDialog("Loading...", false);

        thanksTv = findViewById(R.id.thanks_tv);
        timerTv = findViewById(R.id.timer_tv);
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
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
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

        timerTv.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getApplicationContext());
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                textView.setGravity(Gravity.END);
                return textView;
            }
        });

        Animation textAnimationIn2 = AnimationUtils.
                loadAnimation(this, android.R.anim.fade_in);
        textAnimationIn.setDuration(800);

        Animation textAnimationOut2 = AnimationUtils.
                loadAnimation(this, android.R.anim.fade_out);
        textAnimationOut.setDuration(800);

        timerTv.setInAnimation(textAnimationIn2);
        timerTv.setOutAnimation(textAnimationOut2);


        countDownTimer = new CountDownTimer(11000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTv.setText("Timer: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                nextBtn.performClick();
                countDownTimer.cancel();
                if (nextBtn.getVisibility() == View.VISIBLE) {
                    countDownTimer.start();
                } else {
                    timerTv.setVisibility(View.GONE);
                    countDownTimer.cancel();
                }
            }

        };

        mDatabase = FirebaseDatabase.getInstance();
        questionnaireDataReference = mDatabase.getReference("questionnaire");

        populateQuestionnaire();


        answerList = new ArrayList<>();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                int radioButtonID = answerRg.getCheckedRadioButtonId();
                View radioButton = answerRg.findViewById(radioButtonID);
                int answer = answerRg.indexOfChild(radioButton);
                answerList.add(answer);
                answerRg.clearCheck();
                if (ctr <= questionnaireList.size()) {
                    if (Integer.parseInt(questionnaireList.get(ctr - 1).getAnswer_index()) == answer) {
                        //correct
                        showMessageDialog("You got it right!");
                        numberOfCorrectAnswer++;
                    } else {
                        //incorrect
                        showMessageDialog("I'm sorry but that is not the right answer.");
                    }
                    if (ctr == questionnaireList.size()) {
                        thanksTv.setVisibility(View.VISIBLE);
                        questionnaireContainer.setVisibility(View.GONE);
                        nextBtn.setVisibility(View.GONE);
                        doneBtn.setVisibility(View.VISIBLE);
                        countDownTimer.cancel();
                        timerTv.setVisibility(View.GONE);
                    } else {
                        loadData(ctr);
                        ctr++;
                        countDownTimer.start();
                    }
                }

            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList(StringConstants.ANSWER_LIST, answerList);
                bundle.putInt(StringConstants.CORRECT_ANSWERS, numberOfCorrectAnswer);
                Intent intent = new Intent(getApplicationContext(), EvaluationActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });


    }

    private void populateQuestionnaire() {
        questionnaireDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionnaireList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1 != null) {
                        Questionnaire questionnaire = dataSnapshot1.getValue(Questionnaire.class);
                        questionnaireList.add(questionnaire);

                    }
                }
                hideProgressDialog();
                loadData(ctr);
                ctr++;
                countDownTimer.start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadData(int ctr) {
        questionTv.setText(questionnaireList.get(ctr).getQuestion());
        option1Rb.setText(questionnaireList.get(ctr).getOptions().getOption1());
        option2Rb.setText(questionnaireList.get(ctr).getOptions().getOption2());
        option3Rb.setText(questionnaireList.get(ctr).getOptions().getOption3());
    }

    private void showMessageDialog(String message) {
        if (!this.isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(QuestionnaireActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setMessage(message);
            builder.setCancelable(true);

            final AlertDialog dlg = builder.create();
            dlg.show();

            final Timer t = new Timer();
            t.schedule(new TimerTask() {
                public void run() {
                    dlg.dismiss(); // when the task active then close the dialog
                    t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                }
            }, 1000); // after 1 second (or 1000 miliseconds), the task will be active.
        }

    }

}
