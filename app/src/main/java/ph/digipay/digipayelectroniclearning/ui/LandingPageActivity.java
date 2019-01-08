package ph.digipay.digipayelectroniclearning.ui;

import android.content.Intent;
import android.os.Bundle;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.SharedPrefManager;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.ui.account.LoginActivity;
import ph.digipay.digipayelectroniclearning.ui.account.RegisterActivity;
import ph.digipay.digipayelectroniclearning.ui.admin.AdminMainActivity;
import ph.digipay.digipayelectroniclearning.ui.user.MainActivity;

public class LandingPageActivity extends BaseActivity {

    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.isLogin()) {
            String userType = sharedPrefManager.getLoginUser().getUserType();
            if (userType.equals(StringConstants.TYPE_ADMIN)) {
                startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                finish();
            } else {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

        }

        findViewById(R.id.sign_in_btn).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });

        findViewById(R.id.register_btn).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();
        });
    }
}
