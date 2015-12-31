package com.ayyappan.androidapp.wedlock.invitation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.UILApplication;
import com.ayyappan.androidapp.wedlock.database.local.DBHelper;
import com.ayyappan.androidapp.wedlock.home.AppDetailsDownloader;
import com.ayyappan.androidapp.wedlock.home.ApplicationActivity;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.invitation.bean.Invitation;
import com.ayyappan.androidapp.wedlock.login.activities.LoginActivity;

/**
 * Created by Ayyappan on 20/08/2015.
 */
public class InvitationSelectorActivity extends Activity {
    EditText edit_invitation_id, edit_invitation_code;
    Button btn_access_invitation;

    private RelativeLayout layout; // the layout of the activity
    private UILApplication app;
    DBHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DBHelper(getApplicationContext());

        if(db.retrieveInvitation() != null)
            gotToLoginPage();
        setContentView(R.layout.activity_invitation_selector);

        app = (UILApplication)getApplication();
        layout = (RelativeLayout) findViewById(R.id.iv_background);
        app.setBackground(layout, R.drawable.app_bg_login);

        edit_invitation_id = (EditText) findViewById(R.id.edit_invitation_id);
        edit_invitation_code = (EditText) findViewById(R.id.edit_invitation_passcode);
        btn_access_invitation = (Button) findViewById(R.id.btn_access_invitation);

        btn_access_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptToAccessInvitation();
            }
        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*layout = (LinearLayout) findViewById(R.id.iv_background);
        app.setBackgroundL(layout, R.drawable.app_bg);*/
    }

    private void gotToLoginPage(){
        GlobalData globalData = new GlobalData(getApplicationContext());
        if(globalData.getCouple() == null || globalData.getImagesUrls() ==null){
            new AppDetailsDownloader(getApplicationContext()).execute();
        }
        if(globalData.getUser() == null)
            startActivity(new Intent(InvitationSelectorActivity.this, LoginActivity.class));
        else
            startActivity(new Intent(InvitationSelectorActivity.this, ApplicationActivity.class));
        finish();
    }

    private void attemptToAccessInvitation() {

        String invitationId = edit_invitation_id.getText().toString().trim();
        String invitationPasscode = edit_invitation_code.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

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

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //TODO: Retrieve invitation details from web service
            if (invitationId.equals("1002") && invitationPasscode.equals("1128")) {
                db.insertInvitation(new Invitation(Integer.parseInt(invitationId), Integer.parseInt(invitationPasscode), "Nivedhitha Weds Nivedhitha"));
                gotToLoginPage();
            } else
                Toast.makeText(getApplicationContext(), "Please enter valid invitation details", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}