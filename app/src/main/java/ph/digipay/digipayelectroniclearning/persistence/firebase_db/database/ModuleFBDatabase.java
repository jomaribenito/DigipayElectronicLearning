package ph.digipay.digipayelectroniclearning.persistence.firebase_db.database;

import javax.inject.Inject;

import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.FirebaseDatabaseHelper;

public class ModuleFBDatabase extends FirebaseDatabaseHelper<Module> {

    @Inject
    public ModuleFBDatabase(Class<Module> activityClass) {
        super(activityClass);
    }

}
