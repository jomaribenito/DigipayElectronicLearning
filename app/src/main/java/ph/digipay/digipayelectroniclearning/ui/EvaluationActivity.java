package ph.digipay.digipayelectroniclearning.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.QuestionsList;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;

public class EvaluationActivity extends BaseActivity {

    RecyclerView recyclerView;
    private ArrayList<Integer> correctAnsIndexList;
    private ArrayList<Integer> answerList;
    EvaluationRecyclerAdapter evaluationRecyclerAdapter;
    private TextView resultTv;
    public static final String RESULT_FORMAT = "You've got %s out of %s questions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        recyclerView = findViewById(R.id.recycler_view);
        resultTv = findViewById(R.id.result_tv);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            correctAnsIndexList = bundle.getIntegerArrayList(StringConstants.CORRECT_ANSWERS);
            answerList = bundle.getIntegerArrayList(StringConstants.ANSWER_LIST);

            resultTv.setText(String.format(RESULT_FORMAT, correctAnsIndexList.size(), QuestionsList.populateQuestions().size()));
        }

        evaluationRecyclerAdapter = new EvaluationRecyclerAdapter(this,
                QuestionsList.populateQuestions(), QuestionsList.populateOptions());
        recyclerView.setLayoutManager(new EndlessRecyclerLinearLayoutManager(this));
        recyclerView.setAdapter(evaluationRecyclerAdapter);
    }
}
