package ph.digipay.digipayelectroniclearning.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
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
import ph.digipay.digipayelectroniclearning.models.User;

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
        usersDatabase = database.getReference("users");

        if (BuildConfig.DEBUG) {
            email.setText("test@test.com");
            password.setText("Pass1234!");
        }

        findViewById(R.id.email_sign_in_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog("Signing  in...", false);
                validator.validate();
            }
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
                            if (logEmail.equals(user.getUsername()) && logPassword.equals(user.getPassword())){
                                launchMain();
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

    private void launchMain() {
        sharedPrefManager.setLogin(true);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

}

