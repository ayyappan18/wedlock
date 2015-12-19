package com.ayyappan.androidapp.wedlock.invitation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.HomeActivity;
import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.home.BiographyDetailsDownloader;
import com.ayyappan.androidapp.wedlock.home.GalleryDetailsDownloader;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.login.activities.LoginActivity;
import com.ayyappan.androidapp.wedlock.login.bean.User;
import com.ayyappan.androidapp.wedlock.login.utils.CheckNetwork;
import com.ayyappan.androidapp.wedlock.login.utils.Constants;
import com.ayyappan.androidapp.wedlock.login.utils.ValidateUserInfo;

/**
 * Created by Ayyappan on 20/08/2015.
 */
public class InvitationSelectorActivity extends Activity {
    EditText edit_invitation_id, edit_invitation_code;
    Button btn_access_invitation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_selector);

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
            if (invitationId.equals("1001") && invitationPasscode.equals("1234")) {
                GlobalData globalData = new GlobalData(getApplicationContext());
                if (globalData.getCouple() == null)
                    new BiographyDetailsDownloader(getApplicationContext()).execute();
                if (globalData.getImagesUrls() == null)
                    new GalleryDetailsDownloader(getApplicationContext()).execute();
                startActivity(new Intent(InvitationSelectorActivity.this, LoginActivity.class));
            } else
                Toast.makeText(getApplicationContext(), "Please enter valid invitation details", Toast.LENGTH_SHORT).show();
        }
    }
}