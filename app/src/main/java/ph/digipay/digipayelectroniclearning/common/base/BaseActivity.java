package ph.digipay.digipayelectroniclearning.common.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

import ph.digipay.digipayelectroniclearning.DigipayELearningApplication;
import ph.digipay.digipayelectroniclearning.R;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setUpToolbar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setUpToolbar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        setUpToolbar();
    }

    public void setUpToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public DigipayELearningApplication getDigipayELearningApplication(){
        return (DigipayELearningApplication) getApplication();
    }

    public void showProgressDialog(String message, boolean cancelable) {
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.setCanceledOnTouchOutside(cancelable);
        showProgressDialog(message);
    }

    public void showProgressDialog(String message) {
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public void showProgressDialog(String title, String message, boolean cancelable) {
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.show();
    }

    public void showProgressDialog(int titleId, int messageId, boolean cancelable) {
        mProgressDialog.setTitle(titleId <= 0 ? "" : getString(titleId));
        mProgressDialog.setMessage(messageId <= 0 ? "" : getString(messageId));
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    public void showError(String error){
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    public void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void displayErrors(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getApplicationContext());

            if (view instanceof TextInputLayout) {
                ((TextInputLayout) view).setError(message);
            } else if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                showError(message);
            }
        }
    }
}
