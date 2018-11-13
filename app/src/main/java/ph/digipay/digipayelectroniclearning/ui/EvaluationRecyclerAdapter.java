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

public class EvaluationRecyclerAdapter extends RecyclerView.Adapter<EvaluationRecyclerAdapter.EvaluationRecyclerAdapterVH> {

    private List<String> questionsList;
    private Map<Integer, List<String>> optionsMap;
    private Activity activity;
    private ArrayList<Integer> correctAnsIndexList;
    private ArrayList<Integer> answerList;

    public EvaluationRecyclerAdapter(Activity activity, List<String> questionsList, Map<Integer, List<String>> optionsMap) {
        this.activity = activity;
        this.questionsList = questionsList;
        this.optionsMap = optionsMap;
    }

    @NonNull
    @Override
    public EvaluationRecyclerAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new EvaluationRecyclerAdapterVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionnaire, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationRecyclerAdapterVH holder, int i) {
        String question = questionsList.get(i);
        List<String> optionsList = optionsMap.get(i + 1);

        holder.questionTv.setText(question);
        holder.option1Rb.setText(optionsList.get(0));
        holder.option2Rb.setText(optionsList.get(1));

        if (optionsList.size() == 2) {
            holder.option3Rb.setVisibility(View.GONE);
        } else {
            holder.option3Rb.setVisibility(View.VISIBLE);
            holder.option3Rb.setText(optionsList.get(2));
        }

        Bundle bundle = activity.getIntent().getExtras();
        if (bundle != null) {
            correctAnsIndexList = bundle.getIntegerArrayList(StringConstants.CORRECT_ANSWERS);
            answerList = bundle.getIntegerArrayList(StringConstants.ANSWER_LIST);
        }

        if (answerList != null) {
            ((RadioButton) holder.answerRg.getChildAt(answerList.get(i))).setChecked(true);
        }


        if (correctAnsIndexList != null) {
            for (int ans : correctAnsIndexList) {
                if (ans == i) {
                    holder.questionnaireLL.setBackground(activity.getDrawable(R.drawable.border_green));
                    holder.questionTv.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_green_24dp, 0);
                    break;
                } else {
                    holder.questionnaireLL.setBackground(activity.getDrawable(R.drawable.border_red));
                    holder.questionTv.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_cancel_red_24dp, 0);
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
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
