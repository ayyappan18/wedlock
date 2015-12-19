package com.ayyappan.androidapp.wedlock;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.widget.ExpandableListView;

import com.ayyappan.androidapp.wedlock.menudrawer.MenuDrawerActivity;
import com.ayyappan.androidapp.wedlock.menudrawer.adapater.MenuDrawerListAdapter;

public class AboutActivity extends MenuDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        super.onCreateDrawer();
    }

}
