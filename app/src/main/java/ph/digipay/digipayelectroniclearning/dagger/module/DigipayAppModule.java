package ph.digipay.digipayelectroniclearning.dagger.module;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.database.ModuleFBDatabase;

@Module
public class DigipayAppModule {

    private Application mApplication;

    public DigipayAppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides @NonNull
    @Singleton
    public Application provideApplication() {
        return mApplication;
    }

    @Provides @NonNull
    @Singleton
    public Resources provideResources(Application application) {
        return application.getResources();
    }

    @Provides @Singleton @NonNull
    public Context provideContext(){
        return mApplication;
    }


}
