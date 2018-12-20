package ph.digipay.digipayelectroniclearning.ui.admin.questionnaire;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseRecyclerAdapter;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;

public class QuestionnaireRecyclerAdapter extends BaseRecyclerAdapter<Questionnaire, QuestionnaireRecyclerAdapter.QuestionnaireRecyclerAdapterVH> {

    public QuestionnaireRecyclerAdapter(List<Questionnaire> items) {
        super(items);
    }

    @NonNull
    @Override
    public QuestionnaireRecyclerAdapterVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new QuestionnaireRecyclerAdapterVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_questionnaire, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionnaireRecyclerAdapterVH holder, int i) {
        Questionnaire questionnaire = getItem(i);

        holder.questionTv.setText(questionnaire.getQuestion());
        holder.option1Rb.setText(questionnaire.getOptions().getOption1());
        holder.option2Rb.setText(questionnaire.getOptions().getOption2());
        holder.option3Rb.setText(questionnaire.getOptions().getOption3());
        ((RadioButton) holder.answerRg.getChildAt(Integer.parseInt(questionnaire.getAnswerIndex()))).setChecked(true);
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }

    class QuestionnaireRecyclerAdapterVH  extends RecyclerView.ViewHolder {

        private LinearLayout questionnaireLL;
        private TextView questionTv;
        private RadioGroup answerRg;
        private RadioButton option1Rb;
        private RadioButton option2Rb;
        private RadioButton option3Rb;

        QuestionnaireRecyclerAdapterVH(@NonNull View itemView) {
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
