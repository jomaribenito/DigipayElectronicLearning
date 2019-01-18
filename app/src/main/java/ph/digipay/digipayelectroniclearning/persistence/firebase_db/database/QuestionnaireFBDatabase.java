package ph.digipay.digipayelectroniclearning.persistence.firebase_db.database;

import javax.inject.Inject;

import ph.digipay.digipayelectroniclearning.models.Questionnaire;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.FirebaseDatabaseHelper;

public class QuestionnaireFBDatabase extends FirebaseDatabaseHelper<Questionnaire> {

    @Inject
    public QuestionnaireFBDatabase(Class<Questionnaire> activityClass) {
        super(activityClass);
    }
}
