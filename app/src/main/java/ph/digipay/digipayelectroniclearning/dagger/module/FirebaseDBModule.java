package ph.digipay.digipayelectroniclearning.dagger.module;

import com.google.firebase.database.FirebaseDatabase;

import dagger.Provides;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;
import ph.digipay.digipayelectroniclearning.models.VideoForm;

@dagger.Module
public class FirebaseDBModule {

    @Provides
    FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Provides
    Class<Module> provideModule() {
        return Module.class;
    }

    @Provides
    Class<Questionnaire> provideQuestionnaire() {
        return Questionnaire.class;
    }

    @Provides
    Class<PDFForm> providePdfForm() {
        return PDFForm.class;
    }

    @Provides
    Class<VideoForm> provideVideoForm() {
        return VideoForm.class;
    }
}
