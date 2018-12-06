package ph.digipay.digipayelectroniclearning.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import ph.digipay.digipayelectroniclearning.BuildConfig;
import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.models.User;

public class RegisterActivity extends BaseActivity implements Validator.ValidationListener {

    private FirebaseDatabase mDatabase;
    private DatabaseReference userDataReference;

    @Email
    protected TextInputEditText email;

    @Password(min = 8, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE, messageResId = R.string.invalid_password)
    protected TextInputEditText password;

    @ConfirmPassword
    protected TextInputEditText confirmPassword;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseDatabase.getInstance();
        userDataReference = mDatabase.getReference("users");

        validator = new Validator(this);
        validator.setValidationListener(this);

        email = findViewById(R.id.email_tiet);
        password = findViewById(R.id.password_tiet);
        confirmPassword = findViewById(R.id.confirm_password_tiet);

        if (BuildConfig.DEBUG) {
            email.setText("test@test.com");
            password.setText("Pass1234!");
            confirmPassword.setText("Pass1234!");
        }

        findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog("Creating account...", false);
                validator.validate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        writeNewUser(email.getText().toString(), password.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        displayErrors(errors);
        hideProgressDialog();
    }

    private void writeNewUser(final String email, final String password) {
        Query query = userDataReference.orderByChild("username").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    showToast("Email already exists.");
                } else {
                    String userId = userDataReference.push().getKey();
                    incrementCounter(userDataReference);
                    User user = new User(userId);
                    user.setUsername(email);
                    user.setPassword(password);
                    userDataReference.child(userId).setValue(user);
                    finish();
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*Query query = userDataReference.orderByKey().equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //If email exists then toast shows else store the data on new key
                    if (!data.getValue(User.class).getUsername().equals(email)) {
                        String userId = userDataReference.push().getKey();
                        User user = new User(userId);
                        user.setUsername(email);
                        user.setPassword(password);
                        userDataReference.child(userId).setValue(user);
                        finish();
                    } else {
                        showToast("Email already exists.");
                    }
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
            }
        });*/
    }

    public void incrementCounter(DatabaseReference databaseReference) {
        databaseReference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }

                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                if (databaseError != null) {
                    Log.d("TAG","Firebase counter increment failed.");
                } else {
                    Log.d("TAG","Firebase counter increment succeeded.");
                }
            }

        });
    }
}
