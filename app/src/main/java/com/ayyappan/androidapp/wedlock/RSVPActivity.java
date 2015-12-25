package com.ayyappan.androidapp.wedlock;

import android.os.Bundle;

import com.ayyappan.androidapp.wedlock.menudrawer.MenuDrawerActivity;

public class RSVPActivity extends MenuDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsvp);
        super.onCreateDrawer();
    }

}
