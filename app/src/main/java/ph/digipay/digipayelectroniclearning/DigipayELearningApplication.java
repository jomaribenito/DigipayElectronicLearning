package ph.digipay.digipayelectroniclearning;

import android.app.Application;

import ph.digipay.digipayelectroniclearning.dagger.component.DaggerDigipayAppComponent;
import ph.digipay.digipayelectroniclearning.dagger.component.DigipayAppComponent;
import ph.digipay.digipayelectroniclearning.dagger.module.DigipayAppModule;
import ph.digipay.digipayelectroniclearning.dagger.module.FirebaseDBModule;

public class DigipayELearningApplication extends Application {

    private DigipayAppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        setUpDependencies();
    }

    private void setUpDependencies() {
        appComponent = prepareAppComponent();
    }

    private DigipayAppComponent prepareAppComponent() {
        return DaggerDigipayAppComponent.builder()
                .digipayAppModule(new DigipayAppModule(this))
                .firebaseDBModule(new FirebaseDBModule())
                .build();
    }

    public DigipayAppComponent getAppComponent() {
        return appComponent;
    }
}
