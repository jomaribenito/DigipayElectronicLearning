package ph.digipay.digipayelectroniclearning.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.common.utils.SwipeHelper;
import ph.digipay.digipayelectroniclearning.models.Options;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;

public class QuestionnaireManagementActivity extends BaseActivity {

    private RecyclerView questionnaireRv;

    private FirebaseDatabase mDatabase;
    private DatabaseReference questionnaireDataReference;

    private List<Questionnaire> questionnaireList;
    private QuestionnaireRecyclerAdapter questionnaireRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_management);
        showProgressDialog("Loading...", false);
        questionnaireRv = findViewById(R.id.questionnaire_rv);

        mDatabase = FirebaseDatabase.getInstance();
        questionnaireDataReference = mDatabase.getReference("questionnaire");

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        fetchQuestionnaire();

        new SwipeHelper(this, questionnaireRv) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(final int pos) {
                                final Questionnaire questionnaire = questionnaireList.get(pos);
                                alertDialog.setTitle("Delete Questionnaire")
                                        .setMessage("This questionnaire will be deleted.")
                                        .setCancelable(false)
                                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                removeQuestionnaireItem(questionnaire.getId());
                                                questionnaireRecyclerAdapter.notifyDataSetChanged();
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();

                            }
                        }
                ));

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Update",
                        0,
                        Color.parseColor("#C7C7CB"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Questionnaire questionnaire = questionnaireList.get(pos);
                                String options[] = new String[3];
                                options[0] = questionnaire.getOptions().getOption1();
                                options[1] = questionnaire.getOptions().getOption2();
                                options[2] = questionnaire.getOptions().getOption3();

                                Questionnaire newQuestionnaire = new Questionnaire(questionnaire.getId());
                                newQuestionnaire.setId(questionnaire.getId());
                                newQuestionnaire.setQuestion(questionnaire.getQuestion());
                                newQuestionnaire.setOptions(new Options(options[0], options[1], options[2]));
                                newQuestionnaire.setAnswer_index(questionnaire.getAnswer_index());

                                Intent i = new Intent(getApplicationContext(), QuestionnaireFormActivity.class);
                                i.putExtra(StringConstants.QUESTIONNAIRE_FORMS, newQuestionnaire);
                                startActivity(i);
                            }
                        }
                ));
            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), QuestionnaireFormActivity.class));
            }
        });
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
                questionnaireRecyclerAdapter = new QuestionnaireRecyclerAdapter(questionnaireList);
                questionnaireRv.setLayoutManager(new EndlessRecyclerLinearLayoutManager(getApplicationContext()));
                questionnaireRv.setAdapter(questionnaireRecyclerAdapter);
                hideProgressDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void removeQuestionnaireItem(String uid){
        questionnaireDataReference.child(uid).removeValue();
        /*Query query = questionnaireDataReference.orderByChild("id").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Questionnaire questionnaire = snapshot.getValue(Questionnaire.class);
                    if (questionnaire != null)
                        questionnaireDataReference.child(questionnaire.getId()).removeValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showError(databaseError.getMessage());
            }
        });*/
    }

    @Override
    public void setUpToolbar() {
        super.setUpToolbar();
        getSupportActionBar().setTitle(R.string.app_name);
    }
}
