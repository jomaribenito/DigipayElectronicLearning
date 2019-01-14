package ph.digipay.digipayelectroniclearning.dagger.module;

import javax.inject.Singleton;

import dagger.Provides;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;
import ph.digipay.digipayelectroniclearning.models.VideoForm;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.database.ModuleFBDatabase;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.database.PDFFormFBDatabase;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.database.QuestionnaireFBDatabase;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.database.VideoFormFBDatabase;

@dagger.Module
public class FirebaseDBModule {

    @Provides
    public ModuleFBDatabase provideModuleFBDatabase(Class<Module> moduleClass) {
        return new ModuleFBDatabase(moduleClass);
    }

    @Provides
    public QuestionnaireFBDatabase provideQuestionnaireFBDatabase(Class<Questionnaire> questionnaireClass){
        return new QuestionnaireFBDatabase(questionnaireClass);
    }

    @Provides
    public PDFFormFBDatabase providePdfFormFBDatabase(Class<PDFForm> pdfFormClass){
        return new PDFFormFBDatabase(pdfFormClass);
    }

    @Provides
    public VideoFormFBDatabase provideVideoFormFBDatabase(Class<VideoForm> videoFormClass){
        return new VideoFormFBDatabase(videoFormClass);
    }
}
