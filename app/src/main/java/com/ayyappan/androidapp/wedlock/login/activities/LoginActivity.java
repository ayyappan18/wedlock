package com.ayyappan.androidapp.wedlock.login.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.HomeActivity;
import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.invitation.InvitationSelectorActivity;
import com.ayyappan.androidapp.wedlock.login.bean.User;
import com.ayyappan.androidapp.wedlock.login.utils.CheckNetwork;
import com.ayyappan.androidapp.wedlock.login.utils.Constants;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;

    private SignInButton mPlusSignInButton;
    private LoginButton facebookLoginButton;
    private Button manualRegistrationButton;

    private CallbackManager callbackManager;
    ProgressDialog ringProgressDialog;

    GlobalData globalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        globalData = new GlobalData(getApplicationContext());
        if (globalData.getUser() == null) {
            setContentView(R.layout.activity_login);

            //Check if internet is enabled
            CheckNetwork checkNetwork = new CheckNetwork();
            if (checkNetwork.isOnline(LoginActivity.this)) {
                initialiseLogin();
            }

        } else {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
    }

    private void initialiseLogin() {

        //Facebook Login
        facebookLoginButton = (LoginButton) findViewById(R.id.f_sign_in_button);
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        //Check if app is already loggedIn facebook
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null)
            getFbUserAndRedirectToHome(accessToken);
        else {
            registerFacebookCallback();
        }

        //Google+ Login
        mPlusSignInButton = (SignInButton) findViewById(R.id.g_sign_in_button);
        mPlusSignInButton.setSize(SignInButton.SIZE_WIDE);
        mPlusSignInButton.setOnClickListener(googlePlusSignInClicked());

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();

        //Manual registration
        manualRegistrationButton = (Button) findViewById(R.id.manual_registration_button);
        manualRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }

    private void registerFacebookCallback() {

        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getFbUserAndRedirectToHome(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "User cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this, "Error on Login, check your facebook app_id", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getFbUserAndRedirectToHome(AccessToken accessToken) {
        final User fbUser = new User();
        GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject user, GraphResponse graphResponse) {


                fbUser.setName(user.optString("name"));
                fbUser.setAuthProvider("Facebook");
                fbUser.setPlace("Unknown");

                try {
                    if (user.has("email"))
                        fbUser.setEmail(user.optString("email"));
                    else
                        fbUser.setEmail("Unknown");

                } catch (Exception e) {
                    fbUser.setEmail("Unknown");
                    e.printStackTrace();
                }

                try {
                    if (user.has("picture"))
                        fbUser.setPhoto(user.getJSONObject("picture").getJSONObject("data").getString("url"));
                    else
                        fbUser.setPhoto("Unknown");
                } catch (Exception e) {
                    fbUser.setPhoto("Unknown");
                    e.printStackTrace();
                }

                new GlobalData(getApplicationContext()).setUserName(user.optString("name"));
                redirectLoggedInUserToHome(fbUser);
            }
        }).executeAsync();
    }

    private View.OnClickListener googlePlusSignInClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringProgressDialog = ProgressDialog.show(LoginActivity.this, "Connecting...", "Atempting to connect", true);
                ringProgressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mShouldResolve = true;
                            mGoogleApiClient.connect();
                        } catch (Exception e) {
                            ringProgressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        };
    }

    //GooglePlus
    @Override
    public void onConnected(Bundle bundle) {
        mShouldResolve = false;
        getGooglePlusProfileInformation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        ringProgressDialog.dismiss();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        Log.d(Constants.TAG_LOGIN, "onConnectionFailed:" + connectionResult);
        ringProgressDialog.dismiss();

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(Constants.TAG_LOGIN, "Could not resolve ConnectionResult.", e);
                    Toast.makeText(LoginActivity.this, "Could not resolve ConnectionResult", Toast.LENGTH_LONG).show();
                    mIsResolving = false;
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                Toast.makeText(LoginActivity.this, "Error on Login, check your google + login method", Toast.LENGTH_LONG).show();
            }
        } else {
            // Show the signed-out UI
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null)
            if (mGoogleApiClient.isConnected())
                mGoogleApiClient.disconnect();

        LoginManager.getInstance().logOut();
    }

    /**
     * Fetching user's information name, email, profile pic
     */
    private void getGooglePlusProfileInformation() {
        ringProgressDialog.dismiss();
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi
                    .getCurrentPerson(mGoogleApiClient);

            String personName = currentPerson.getDisplayName();
            String personPhotoUrl = currentPerson.getImage().getUrl();
            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            String location = currentPerson.getCurrentLocation();
            //String personGooglePlusProfile = currentPerson.getUrl();

            User googleUser = new User();

            googleUser.setName(personName);
            googleUser.setAuthProvider("Google Plus");
            googleUser.setEmail(email);
            googleUser.setPlace(location);
            googleUser.setPhoto(personPhotoUrl);

            new GlobalData(getApplicationContext()).setUserName(personName);
            redirectLoggedInUserToHome(googleUser);
            // by default the profile url gives 50x50 px image only
            // we can replace the value with whatever dimension we want by
            // replacing sz=X
//                personPhotoUrl = personPhotoUrl.substring(0,
//                        personPhotoUrl.length() - 2)
//                        + PROFILE_PIC_SIZE;

            //new LoadProfileImage().execute(personPhotoUrl);

        } else {
            Toast.makeText(getApplicationContext(),
                    "Person information is null", Toast.LENGTH_LONG).show();
        }
    }

    private void redirectLoggedInUserToHome(User user) {
        globalData.setUser(user);
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(callbackManager!=null)
            callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this, InvitationSelectorActivity.class));
        finish();
    }
}

