package ph.digipay.digipayelectroniclearning.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;

public class EvaluationRecyclerAdapter extends RecyclerView.Adapter<EvaluationRecyclerAdapter.EvaluationRecyclerAdapterVH> {

    private Activity activity;
    private ArrayList<Integer> answerList;

    private List<Questionnaire> questionnaireList;

    public EvaluationRecyclerAdapter(Activity activity, List<Questionnaire> questionnaireList) {
        this.activity = activity;
        this.questionnaireList = questionnaireList;
    }


    @NonNull
    @Override
    public EvaluationRecyclerAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new EvaluationRecyclerAdapterVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionnaire, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationRecyclerAdapterVH holder, int i) {
        Questionnaire questionnaire = questionnaireList.get(i);

        holder.questionTv.setText(questionnaire.getQuestion());
        holder.option1Rb.setText(questionnaire.getOptions().getOption1());
        holder.option2Rb.setText(questionnaire.getOptions().getOption2());
        holder.option3Rb.setText(questionnaire.getOptions().getOption3());
//        ((RadioButton) holder.answerRg.getChildAt(Integer.parseInt(questionnaire.getAnswer_index()))).setChecked(true);


        Bundle bundle = activity.getIntent().getExtras();
        if (bundle != null) {
            answerList = bundle.getIntegerArrayList(StringConstants.ANSWER_LIST);
        }

        if (answerList != null) {
            if (answerList.get(i) != -1) {
                ((RadioButton) holder.answerRg.getChildAt(answerList.get(i))).setChecked(true);
            }
            if (answerList.get(i) == Integer.parseInt(questionnaire.getAnswer_index())) {
                holder.questionnaireLL.setBackground(activity.getDrawable(R.drawable.border_green));
                holder.questionTv.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_green_24dp, 0);
            } else {
                holder.questionnaireLL.setBackground(activity.getDrawable(R.drawable.border_red));
                holder.questionTv.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_cancel_red_24dp, 0);
            }
        }

    }

    @Override
    public int getItemCount() {
        return questionnaireList.size();
    }

    class EvaluationRecyclerAdapterVH extends RecyclerView.ViewHolder {

        private LinearLayout questionnaireLL;
        private TextView questionTv;
        private RadioGroup answerRg;
        private RadioButton option1Rb;
        private RadioButton option2Rb;
        private RadioButton option3Rb;

        public EvaluationRecyclerAdapterVH(@NonNull View itemView) {
            super(itemView);
            questionnaireLL = itemView.findViewById(R.id.questionnaire_ll);
            questionTv = itemView.findViewById(R.id.question_tv);
            answerRg = itemView.findViewById(R.id.answer_rg);
            option1Rb = itemView.findViewById(R.id.option1_rb);
            option2Rb = itemView.findViewById(R.id.option2_rb);
            option3Rb = itemView.findViewById(R.id.option3_rb);
        }
    }
}
