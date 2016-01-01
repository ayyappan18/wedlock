package com.ayyappan.androidapp.wedlock.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.database.local.DBHelper;
import com.ayyappan.androidapp.wedlock.tasks.DownloadAppDetailsTask;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.invitation.bean.Invitation;
import com.ayyappan.androidapp.wedlock.login.utils.CheckNetwork;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ayyappan on 20/08/2015.
 */
public class InvitationSelectorActivity extends Activity {
    EditText edit_invitation_id, edit_invitation_code;
    Button btn_access_invitation;

    DBHelper localDB;

    String invitationId,invitationPasscode = new String("");

    boolean cancel = false;
    View focusView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localDB = new DBHelper(getApplicationContext());

        if(localDB.retrieveInvitation() != null)
            gotToLoginPage();

        setContentView(R.layout.activity_invitation_selector);

        edit_invitation_id = (EditText) findViewById(R.id.edit_invitation_id);
        edit_invitation_code = (EditText) findViewById(R.id.edit_invitation_passcode);
        btn_access_invitation = (Button) findViewById(R.id.btn_access_invitation);

        btn_access_invitation.setOnClickListener(accessInvitationListener());

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private View.OnClickListener accessInvitationListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()){
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    if(focusView!=null)
                        focusView.requestFocus();
                }
                else {
                    if (invitationId.equals("1002") && invitationPasscode.equals("1128")) {
                        localDB.insertInvitation(new Invitation(Integer.parseInt(invitationId), Integer.parseInt(invitationPasscode), "Nivedhitha Weds Nivedhitha"));
                        gotToLoginPage();
                    } else
                        Toast.makeText(getApplicationContext(), "Please enter valid invitation details", Toast.LENGTH_SHORT).show();

                }
            }
        };
    }

    private boolean validateForm(){

        //Get Field values
        invitationId = edit_invitation_id.getText().toString().trim();
        invitationPasscode = edit_invitation_code.getText().toString().trim();

        // Field validdation
        if (TextUtils.isEmpty(invitationId)) {
            edit_invitation_id.setError(getString(R.string.error_field_required));
            focusView = edit_invitation_id;
            cancel = true;
        } else if (!TextUtils.isDigitsOnly(invitationId)) {
            edit_invitation_id.setError(getString(R.string.error_field_only_digits));
            focusView = edit_invitation_id;
            cancel = true;
        }
        if (TextUtils.isEmpty(invitationPasscode)) {
            edit_invitation_code.setError(getString(R.string.error_field_required));
            focusView = edit_invitation_code;
            cancel = true;
        } else if (!TextUtils.isDigitsOnly(invitationPasscode)) {
            edit_invitation_code.setError(getString(R.string.error_field_only_digits));
            focusView = edit_invitation_code;
            cancel = true;
        }

        return cancel;
    }

    private void gotToLoginPage(){
        CheckNetwork checkNetwork = new CheckNetwork();
        GlobalData globalData = new GlobalData(getApplicationContext());
        if(checkNetwork.isConnected(getApplicationContext())) {
            if (globalData.getCouple() == null || globalData.getImagesUrls() == null) {
                new DownloadAppDetailsTask(getApplicationContext()).execute();
            }
        }

        if(globalData.getUser() == null)
            startActivity(new Intent(InvitationSelectorActivity.this, LoginActivity.class));
        else
            startActivity(new Intent(InvitationSelectorActivity.this, ApplicationActivity.class));
        finish();
    }

}