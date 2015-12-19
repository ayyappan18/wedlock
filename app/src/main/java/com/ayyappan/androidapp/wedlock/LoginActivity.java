package com.ayyappan.androidapp.wedlock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private FacebookCallback Callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            System.out.println("Success");

            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
          //  if(profile!= null){
                String firstName = profile.getFirstName();
                new GlobalData(getApplicationContext()).setUserName(firstName);

                startActivity(new Intent(LoginActivity.this, HomeActivity.class));

         //   }
         /*   GraphRequest.newMeRequest(
                    loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject json, GraphResponse response) {
                            if (response.getError() != null) {
                                // handle error
                                System.out.println("ERROR");
                            } else {
                                System.out.println("Success");
                                try {

                                    String jsonresult = String.valueOf(json);
                                    System.out.println("JSON Result" + jsonresult);

                                    String str_email = json.getString("email");
                                    String str_id = json.getString("id");
                                    String str_firstname = json.getString("first_name");
                                    String str_lastname = json.getString("last_name");
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }).executeAsync();*/

        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException exception) {
            // App code
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize SDK before setContentView(Layout ID)

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                if(newAccessToken!=null){
                    Profile profile = Profile.getCurrentProfile();
                //    if(profile!= null){
                        String firstName = profile.getFirstName();
                        new GlobalData(getApplicationContext()).setUserName(firstName);

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                //    }
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
                else {
                    callbackManager = CallbackManager.Factory.create();

                    LoginManager.getInstance().registerCallback(callbackManager, Callback);

                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(
                                Profile oldProfile,
                                Profile currentProfile) {
                            // App code
                        }
                    };

                    setContentView(R.layout.activity_old_login);
                }
            }
        };
        if(accessToken!=null){
            Profile profile = Profile.getCurrentProfile();
            if(profile!= null) {
                String firstName = profile.getFirstName();
                new GlobalData(getApplicationContext()).setUserName(firstName);
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        }
        else {
            callbackManager = CallbackManager.Factory.create();

            LoginManager.getInstance().registerCallback(callbackManager, Callback);

            profileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(
                        Profile oldProfile,
                        Profile currentProfile) {
                    // App code
                }
            };

            setContentView(R.layout.activity_old_login);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }
}
