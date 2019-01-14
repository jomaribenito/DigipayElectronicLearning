package ph.digipay.digipayelectroniclearning.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import ph.digipay.digipayelectroniclearning.dagger.module.DigipayAppModule;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.database.ModuleFBDatabase;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.database.PDFFormFBDatabase;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.database.QuestionnaireFBDatabase;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.database.VideoFormFBDatabase;

@Singleton
@Component(modules = {DigipayAppModule.class})
public interface DigipayAppComponent {
    ModuleFBDatabase getModuleFbDatabase();
    QuestionnaireFBDatabase getQuestionnaireFbDatabase();
    PDFFormFBDatabase getPdfFormFbDatabase();
    VideoFormFBDatabase getVideoFormFbDatabase();
}
