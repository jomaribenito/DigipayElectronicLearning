package ph.digipay.digipayelectroniclearning.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import ph.digipay.digipayelectroniclearning.BuildConfig;
import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.SharedPrefManager;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.models.User;
import ph.digipay.digipayelectroniclearning.ui.admin.AdminMainActivity;
import ph.digipay.digipayelectroniclearning.ui.user.MainActivity;

public class LoginActivity extends BaseActivity implements Validator.ValidationListener {

    @NotEmpty(message = "Invalid Email")
    protected TextInputEditText email;

    @NotEmpty(message = "Invalid Password")
    protected TextInputEditText password;

    private Validator validator;

    private FirebaseDatabase database;
    private DatabaseReference usersDatabase;
    private SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefManager = new SharedPrefManager(this);

        email = findViewById(R.id.email_tiet);
        password = findViewById(R.id.password_tiet);

        validator = new Validator(this);
        validator.setValidationListener(this);

        database = FirebaseDatabase.getInstance();
        usersDatabase = database.getReference(StringConstants.USERS_DB);

        if (BuildConfig.DEBUG) {
            email.setText("test@test.com");
            password.setText("Pass1234!");
        }

        findViewById(R.id.email_sign_in_button).setOnClickListener(v -> {
            showProgressDialog("Signing  in...", false);
            validator.validate();
        });


    }

    @Override
    public void onValidationSucceeded() {
        String logEmail = email.getText().toString();
        String logPassword = password.getText().toString();
        attemptLogin(logEmail, logPassword);
    }

    private void attemptLogin(final String logEmail, final String logPassword) {
        Query query = usersDatabase.orderByChild("username").equalTo(logEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot users : dataSnapshot.getChildren()) {
                        User user = users.getValue(User.class);
                        if (user != null) {
                            if (logEmail.equals(user.getUsername()) && logPassword.equals(user.getPassword())) {
                                launchMain(user);
                            } else {
                                showToast("Incorrect password");
                            }
                        }
                    }
                } else {
                    showToast("No user exist");
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        displayErrors(errors);
    }

    private void launchMain(User user) {
        sharedPrefManager.setLogin(true);
        sharedPrefManager.setLoginUser(user);
        String userType = user.getUserType();
        if (userType.equals(StringConstants.TYPE_ADMIN)) {
            startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

}

