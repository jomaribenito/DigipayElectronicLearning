package ph.digipay.digipayelectroniclearning.ui.user;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;
import ph.digipay.digipayelectroniclearning.ui.common.firebase_db.FirebaseDatabaseHelper;

public class EvaluationActivity extends BaseActivity {

    RecyclerView recyclerView;
    EvaluationRecyclerAdapter evaluationRecyclerAdapter;
    private TextView resultTv;
    public static final String RESULT_FORMAT = "You've got %s out of %s questions";

   private String moduleUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        showProgressDialog("Loading...", false);

        recyclerView = findViewById(R.id.recycler_view);
        resultTv = findViewById(R.id.result_tv);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            ArrayList<Integer> answerList = bundle.getIntegerArrayList(StringConstants.ANSWER_LIST);
            int numberOfCorrectAnswer = bundle.getInt(StringConstants.CORRECT_ANSWERS);
            resultTv.setText(String.format(RESULT_FORMAT, numberOfCorrectAnswer, answerList.size()));
            moduleUid = bundle.getString(StringConstants.MODULE_UID);
        }

        evaluationRecyclerAdapter = new EvaluationRecyclerAdapter(EvaluationActivity.this);
        FirebaseDatabaseHelper<Questionnaire> questionnaireFirebaseDatabase =
                new FirebaseDatabaseHelper<>(Questionnaire.class);
        questionnaireFirebaseDatabase.fetchItems(StringConstants.QUESTIONNAIRE_DB, itemList -> {
            evaluationRecyclerAdapter.clear();
            List<Questionnaire> questionnaireList = new ArrayList<>();
            for (Questionnaire questionnaire : itemList){
                if (questionnaire.getModuleUid().equals(moduleUid)){
                    questionnaireList.add(questionnaire);
                }
            }
            evaluationRecyclerAdapter.setItems(questionnaireList);
            hideProgressDialog();
        });

        recyclerView.setLayoutManager(new EndlessRecyclerLinearLayoutManager(this));
        recyclerView.setAdapter(evaluationRecyclerAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
