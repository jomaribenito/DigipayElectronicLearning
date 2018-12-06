package ph.digipay.digipayelectroniclearning.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.QuestionsList;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;

public class EvaluationActivity extends BaseActivity {

    RecyclerView recyclerView;
    EvaluationRecyclerAdapter evaluationRecyclerAdapter;
    private TextView resultTv;
    public static final String RESULT_FORMAT = "You've got %s out of %s questions";

    private List<Questionnaire> questionnaireList;

    private FirebaseDatabase mDatabase;
    private DatabaseReference questionnaireDataReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        showProgressDialog("Loading...", false);

        mDatabase = FirebaseDatabase.getInstance();
        questionnaireDataReference = mDatabase.getReference("questionnaire");

        fetchQuestionnaire();

        recyclerView = findViewById(R.id.recycler_view);
        resultTv = findViewById(R.id.result_tv);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            ArrayList<Integer> answerList = bundle.getIntegerArrayList(StringConstants.ANSWER_LIST);
            int numberOfCorrectAnswer = bundle.getInt(StringConstants.CORRECT_ANSWERS);
            resultTv.setText(String.format(RESULT_FORMAT, numberOfCorrectAnswer, answerList.size()));
        }
    }

    private void fetchQuestionnaire(){
        questionnaireDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionnaireList = new ArrayList<>();
                for (DataSnapshot questionnaire : dataSnapshot.getChildren()){
                    if (questionnaire != null) {
                        Questionnaire questionnaire1 = questionnaire.getValue(Questionnaire.class);
                        questionnaireList.add(questionnaire1);
                    }
                }
                evaluationRecyclerAdapter = new EvaluationRecyclerAdapter(EvaluationActivity.this, questionnaireList);
                recyclerView.setLayoutManager(new EndlessRecyclerLinearLayoutManager(EvaluationActivity.this));
                recyclerView.setAdapter(evaluationRecyclerAdapter);
                hideProgressDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
