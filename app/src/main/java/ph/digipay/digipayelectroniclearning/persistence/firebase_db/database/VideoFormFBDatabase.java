package ph.digipay.digipayelectroniclearning.persistence.firebase_db.database;

import javax.inject.Inject;

import ph.digipay.digipayelectroniclearning.models.VideoForm;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.FirebaseDatabaseHelper;

public class VideoFormFBDatabase extends FirebaseDatabaseHelper<VideoForm> {

    @Inject
    public VideoFormFBDatabase(Class<VideoForm> activityClass) {
        super(activityClass);
    }
}
