package com.ayyappan.androidapp.wedlock.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.database.mongolab.PostUserDetailsAsyncTask;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.model.User;
import com.ayyappan.androidapp.wedlock.utils.CheckNetwork;
import com.ayyappan.androidapp.wedlock.utils.Constants;
import com.ayyappan.androidapp.wedlock.utils.ValidateUserInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by AndreBTS on 20/08/2015.
 */
public class RegisterActivity extends Activity {
    EditText edit_nome, edit_location;
    Button btn_registrar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        String email;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            email = extras == null ? "" : extras.getString(Constants.TAG_EMAIL);
        } else {
            email = savedInstanceState.getString(Constants.TAG_EMAIL);
        }

        edit_nome = (EditText) findViewById(R.id.edit_nome);
     //   edit_email = (EditText) findViewById(R.id.edit_email);
      //  edit_email.setText(email);
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
     //   String email = edit_email.getText().toString();
        String location = edit_location.getText().toString();

        boolean cancel = false;
        View focusView = null;

        ValidateUserInfo validate = new ValidateUserInfo();

        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            edit_nome.setError(getString(R.string.error_field_required));
            focusView = edit_nome;
            cancel = true;
      /*  } else if (TextUtils.isEmpty(email)) {
            edit_email.setError(getString(R.string.error_field_required));
            focusView = edit_email;
            cancel = true;
        } else if (!validate.isEmailValid(email)) {
            edit_email.setError(getString(R.string.error_invalid_email));
            focusView = edit_email;
            cancel = true;*/
        } else {
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
            registeredUser.setEmail("Unknown");
            registeredUser.setAuthProvider("Manual");
            registeredUser.setPlace(location);
            registeredUser.setPhoto("Unknown");

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss a");
            String date = sdf.format(c.getTime());
            registeredUser.setDateOfLogin(date);

            CheckNetwork checkNetwork = new CheckNetwork();
            if (checkNetwork.isConnected(RegisterActivity.this)) {
                new PostUserDetailsAsyncTask().execute(registeredUser);
            }

            new GlobalData(RegisterActivity.this).setUser(registeredUser);
            startActivity(new Intent(RegisterActivity.this, ApplicationActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
}