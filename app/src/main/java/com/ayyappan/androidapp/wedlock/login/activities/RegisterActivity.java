package com.ayyappan.androidapp.wedlock.login.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.home.ApplicationActivity;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.login.bean.User;
import com.ayyappan.androidapp.wedlock.login.utils.CheckNetwork;
import com.ayyappan.androidapp.wedlock.login.utils.Constants;
import com.ayyappan.androidapp.wedlock.login.utils.ValidateUserInfo;

/**
 * Created by AndreBTS on 20/08/2015.
 */
public class RegisterActivity extends Activity {
    EditText edit_nome, edit_email, edit_location;
    Button btn_registrar;
    private CreateUserTask mCreateTask = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        String email;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            email = extras == null ? "" : extras.getString(Constants.TAG_EMAIL);
        } else {
            email = savedInstanceState.getString(Constants.TAG_EMAIL);
        }

        edit_nome = (EditText) findViewById(R.id.edit_nome);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_email.setText(email);
        edit_location = (EditText) findViewById(R.id.edit_location);

        btn_registrar = (Button) findViewById(R.id.btn_register);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCreate();
            }
        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptCreate() {
        // Store values at the time of the login attempt.
        String name = edit_nome.getText().toString();
        String email = edit_email.getText().toString();
        String location = edit_location.getText().toString();

        boolean cancel = false;
        View focusView = null;

        ValidateUserInfo validate = new ValidateUserInfo();

        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            edit_nome.setError(getString(R.string.error_field_required));
            focusView = edit_nome;
            cancel = true;
        } else if (TextUtils.isEmpty(email)) {
            edit_email.setError(getString(R.string.error_field_required));
            focusView = edit_email;
            cancel = true;
        } /*else if (validate.isEmailValid(email)) {
            edit_email.setError(getString(R.string.error_invalid_email));
            focusView = edit_email;
            cancel = true;
        } */else {
            // Check for a valid password, if the user entered one.
            if (TextUtils.isEmpty(location)) {
                edit_location.setError(getString(R.string.error_field_required));
                focusView = edit_location;
                cancel = true;
            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            User registeredUser = new User();
            registeredUser.setName(name);
            registeredUser.setEmail(email);
            registeredUser.setAuthProvider("Manual");
            registeredUser.setPlace(location);
            registeredUser.setPhoto("Unknown");

            CheckNetwork checkNetwork = new CheckNetwork();
            if (checkNetwork.isOnline(RegisterActivity.this)) {
                //TODO: Send user data to web service
            }

            new GlobalData(RegisterActivity.this).setUser(registeredUser);
            startActivity(new Intent(RegisterActivity.this, ApplicationActivity.class));



            //TODO Create account logic
            // Show a progress spinner, and kick off a background task to
            // perform the user registration attempt.
          /*  mCreateTask = new CreateUserTask(name, email, location);
            mCreateTask.execute((Void) null);
        }*/
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class CreateUserTask extends AsyncTask<Void, Void, Boolean> {
        private final String mName;
        private final String mEmail;
        private final String mPassword;

        CreateUserTask(String name, String email, String password) {
            mName = name;
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: check if account already exists against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: if there's no account registered, register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mCreateTask = null;
            CheckNetwork checkNetwork = new CheckNetwork();
            if (checkNetwork.isConnected(RegisterActivity.this) && success) {
                Toast.makeText(RegisterActivity.this, "Account created", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mCreateTask = null;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
}