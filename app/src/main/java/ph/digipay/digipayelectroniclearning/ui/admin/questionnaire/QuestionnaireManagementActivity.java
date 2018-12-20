package ph.digipay.digipayelectroniclearning.ui.admin.questionnaire;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.common.utils.SwipeHelper;
import ph.digipay.digipayelectroniclearning.models.Options;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;
import ph.digipay.digipayelectroniclearning.ui.common.firebase_db.FirebaseDatabaseHelper;

public class QuestionnaireManagementActivity extends BaseActivity {

    private RecyclerView questionnaireRv;

    private QuestionnaireRecyclerAdapter questionnaireRecyclerAdapter;

    private FirebaseDatabaseHelper<Questionnaire> questionnaireFirebaseDatabase;
    private List<Questionnaire> questionnaireList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_management);
        questionnaireRv = findViewById(R.id.questionnaire_rv);

        questionnaireFirebaseDatabase = new FirebaseDatabaseHelper<>(Questionnaire.class);

        questionnaireFirebaseDatabase.fetchItems(StringConstants.QUESTIONNAIRE_DB, itemList -> {
            questionnaireList = itemList;
            questionnaireRecyclerAdapter = new QuestionnaireRecyclerAdapter(questionnaireList);
            questionnaireRv.setLayoutManager(new EndlessRecyclerLinearLayoutManager(getApplicationContext()));
            questionnaireRv.setAdapter(questionnaireRecyclerAdapter);
        });


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        new SwipeHelper(this, questionnaireRv) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        pos -> {
                            final Questionnaire questionnaire = questionnaireList.get(pos);
                            alertDialog.setTitle("Delete Questionnaire")
                                    .setMessage("This questionnaire will be deleted.")
                                    .setCancelable(false)
                                    .setPositiveButton("Delete", (dialog, which) -> {
//                                        removeQuestionnaireItem(questionnaire.getUid());
                                        questionnaireFirebaseDatabase.removeItem(StringConstants.QUESTIONNAIRE_DB, questionnaire.getUid());
                                        questionnaireRecyclerAdapter.clear();
                                    })
                                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                                    .show();

                        }
                ));

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Update",
                        0,
                        Color.parseColor("#C7C7CB"),
                        pos -> {
                            Questionnaire questionnaire = questionnaireList.get(pos);

                            String options[] = new String[3];
                            options[0] = questionnaire.getOptions().getOption1();
                            options[1] = questionnaire.getOptions().getOption2();
                            options[2] = questionnaire.getOptions().getOption3();

                            Questionnaire newQuestionnaire = new Questionnaire();
                            newQuestionnaire.setUid(questionnaire.getUid());
                            newQuestionnaire.setQuestion(questionnaire.getQuestion());
                            newQuestionnaire.setOptions(new Options(options[0], options[1], options[2]));
                            newQuestionnaire.setAnswerIndex(questionnaire.getAnswerIndex());

                            Intent i = new Intent(getApplicationContext(), QuestionnaireFormActivity.class);
                            i.putExtra(StringConstants.QUESTIONNAIRE_FORMS, newQuestionnaire);
                            startActivity(i);
                            questionnaireRecyclerAdapter.clear();
                        }
                ));
            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), QuestionnaireFormActivity.class));
            questionnaireRecyclerAdapter.clear();
        });
    }

    @Override
    public void setUpToolbar() {
        super.setUpToolbar();
        getSupportActionBar().setTitle(R.string.app_name);
    }
}
