package com.ayyappan.androidapp.wedlock.menudrawer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.UILApplication;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.menudrawer.adapater.MenuDrawerListAdapter;
import com.ayyappan.androidapp.wedlock.menudrawer.utils.IconDecoder;
import com.ayyappan.androidapp.wedlock.venue.bean.Venue;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.ABOUT;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.BIOGRAPHY;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.BIOGRAPHY_BRIDE;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.BIOGRAPHY_GROOM;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.ENTERTAINMENT;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.ENTERTAINMENT_LIGHTMUSIC;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.ENTERTAINMENT_SANGEETH;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.GALLERY;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.HOME;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.INVITATION;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.EVENTS;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.EVENT_RECEPTION;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.EVENT_WEDDING;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.getMenuGroupCompleteList;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.getMenuGroupHeaders;

/**
 * Created by Ayyappan on 03/11/2015.
 */
public class MenuDrawerActivity extends AppCompatActivity {

    //menu drawer
    private ActionBarDrawerToggle drawerToggle;

    //expandable list view
    private ExpandableListView drawerList;
    private static MenuDrawerListAdapter listAdapter = null;

    private int lastExpandedPosition = -1;

    protected void onCreateDrawer()
    {

        UILApplication app = (UILApplication) getApplication();
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.iv_background);
        if(layout!=null) {
            app.setBackground(layout, R.drawable.app_bg);
        }


        // R.id.drawer_layout should be in every activity with exactly the same id.
       // drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //find the drawer layout
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //find the expandable list drawer
        drawerList = (ExpandableListView) findViewById(R.id.left_drawer);

        //create expandable list adapter
        if(listAdapter == null)
        listAdapter = new MenuDrawerListAdapter(this, getMenuGroupHeaders(), getMenuGroupCompleteList(),
                IconDecoder.getMenuIconBitMaps(getApplicationContext()));

        //initialise expandable list view with expandable list adapter
        drawerList.setAdapter(listAdapter);

        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //action when user clicks menu group header
        drawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                String groupName = getMenuGroupHeaders().get(groupPosition);

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //action when user clicks menu group child elements
        drawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String groupName = getMenuGroupHeaders().get(groupPosition);
                String childName = getMenuGroupCompleteList().get(groupName).get(childPosition);
                Intent intent = new Intent(MenuDrawerActivity.this,MenuDrawerActivity.class);


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);


                  startActivity(intent);
                finish();

                return true;
            }
        });

        //Collapse already expanded menu group when user expands new menu group
        drawerList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    drawerList.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    /*    layout = (RelativeLayout) findViewById(R.id.iv_background);
        if(layout!=null)
        app.setBackground(layout, R.drawable.app_bg);*/
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        finish();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
