package com.ayyappan.androidapp.wedlock;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayyappan.androidapp.wedlock.home.AppDetailsDownloader;
import com.ayyappan.androidapp.wedlock.home.BiographyDetailsDownloader;
import com.ayyappan.androidapp.wedlock.home.GalleryDetailsDownloader;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.imageslideshow.ImageViewerActivity;
import com.ayyappan.androidapp.wedlock.login.utils.CheckNetwork;
import com.ayyappan.androidapp.wedlock.menudrawer.MenuDrawerActivity;
import com.ayyappan.androidapp.wedlock.venue.VenueActivity;
import com.ayyappan.androidapp.wedlock.venue.bean.Venue;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends MenuDrawerActivity {


    private TextView txtTimerDay, txtTimerHour, txtTimerMinute, txtTimerSecond, txtUsername;
    private TextView tvEvent;
    private Handler handler;
    private Runnable runnable;
    private ProfileTracker profileTracker;
    private CallbackManager callbackManager;
    private RelativeLayout goingLayout;
    private RelativeLayout eventRespondLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.onCreateDrawer();

        CheckNetwork checkNetwork = new CheckNetwork();

        if(checkNetwork.isOnline(getApplicationContext())){
            new AppDetailsDownloader(getApplicationContext()).execute();
        }

       // initialiseFaceBookAcessTokenTracker();

        eventRespondLayout = (RelativeLayout)findViewById(R.id.EventRespond);
        Button clickGoingButton = (Button) findViewById(R.id.going);
        goingLayout = (RelativeLayout)findViewById(R.id.GoingResponse);
        clickGoingButton.setOnClickListener(GoingEventButtonListener());

        Button clickMaybeButton = (Button) findViewById(R.id.maybe);
        clickMaybeButton.setOnClickListener(MaybeEventButtonListener());

        txtTimerDay = (TextView) findViewById(R.id.txtTimerDay);
        txtTimerHour = (TextView) findViewById(R.id.txtTimerHour);
        txtTimerMinute = (TextView) findViewById(R.id.txtTimerMinute);
        txtTimerSecond = (TextView) findViewById(R.id.txtTimerSecond);
        tvEvent = (TextView) findViewById(R.id.tvhappyevent);

        String firstName = new GlobalData(getApplicationContext()).getUser().getName();
        txtUsername = (TextView) findViewById(R.id.username);
        txtUsername.setText("Welcome " + firstName + ",");

        countDownStart();
    }


    /*
    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {

     //TODO check if profile exist

            Profile profile = Profile.getCurrentProfile();

            if(profile!=null) {
                String firstName = profile.getFirstName();
                GlobalData.setUserName(firstName);
                System.out.println(profile.getProfilePictureUri(20, 20));
                System.out.println(profile.getLinkUri());


            }
        }
    }

    private void initialiseFaceBookAcessTokenTracker() {

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        updateWithToken(accessToken);

  /*      new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };

    }
    */

    private View.OnClickListener GoingEventButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventRespondLayout.setVisibility(View.INVISIBLE);
                goingLayout.setVisibility(View.VISIBLE);

            }
        };
    }

    private View.OnClickListener MaybeEventButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Send event to FB

            }
        };
    }

    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    // Please here set your event date//YYYY-MM-DD
                    Date futureDate = dateFormat.parse("2016-02-10");
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        txtTimerDay.setText("" + String.format("%02d", days));
                        txtTimerHour.setText("" + String.format("%02d", hours));
                        txtTimerMinute.setText(""
                                + String.format("%02d", minutes));
                        txtTimerSecond.setText(""
                                + String.format("%02d", seconds));
                    } else {
                        tvEvent.setVisibility(View.VISIBLE);
                        tvEvent.setText("Start event now!");
                        textViewGone();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }

    public void textViewGone() {
        findViewById(R.id.txtTimerDay).setVisibility(View.GONE);
        findViewById(R.id.txt_TimerDay).setVisibility(View.GONE);
        findViewById(R.id.txtTimerHour).setVisibility(View.GONE);
        findViewById(R.id.txt_TimerHour).setVisibility(View.GONE);
        findViewById(R.id.txtTimerMinute).setVisibility(View.GONE);
        findViewById(R.id.txt_TimerMinute).setVisibility(View.GONE);
        findViewById(R.id.txtTimerSecond).setVisibility(View.GONE);
        findViewById(R.id.txt_TimerSecond).setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
