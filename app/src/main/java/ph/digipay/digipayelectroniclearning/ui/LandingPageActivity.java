package ph.digipay.digipayelectroniclearning.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.SharedPrefManager;
import ph.digipay.digipayelectroniclearning.ui.admin.AdminMainActivity;

public class LandingPageActivity extends BaseActivity {

    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.isLogin()){
            startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
            finish();
        }

        findViewById(R.id.sign_in_btn).setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));

        findViewById(R.id.register_btn).setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
    }
}
