package ph.digipay.digipayelectroniclearning.persistence.firebase_db.database;

import javax.inject.Inject;

import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.FirebaseDatabaseHelper;

public class PDFFormFBDatabase extends FirebaseDatabaseHelper<PDFForm> {

    @Inject
    public PDFFormFBDatabase(Class<PDFForm> activityClass) {
        super(activityClass);
    }
}
