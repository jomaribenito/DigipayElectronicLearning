package ph.digipay.digipayelectroniclearning.common.base;

import android.support.v7.app.AppCompatActivity;

import ph.digipay.digipayelectroniclearning.DigipayELearningApplication;

public abstract class BaseActivity extends AppCompatActivity {

    public DigipayELearningApplication getDigipayELearningApplication(){
        return (DigipayELearningApplication) getApplication();
    }
}
