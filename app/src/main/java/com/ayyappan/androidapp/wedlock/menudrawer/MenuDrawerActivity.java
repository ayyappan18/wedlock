package com.ayyappan.androidapp.wedlock.menudrawer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.AboutActivity;
import com.ayyappan.androidapp.wedlock.HomeActivity;
import com.ayyappan.androidapp.wedlock.InvitationActivity;
import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.biography.BiographyActivity;
import com.ayyappan.androidapp.wedlock.entertainment.LightMusicActivity;
import com.ayyappan.androidapp.wedlock.gallery.GalleryActivity;
import com.ayyappan.androidapp.wedlock.gallery.GalleryGridActivity;
import com.ayyappan.androidapp.wedlock.menudrawer.adapater.MenuDrawerListAdapter;
import com.ayyappan.androidapp.wedlock.venue.VenueActivity;
import com.ayyappan.androidapp.wedlock.venue.bean.Venue;

import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.ABOUT;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.BIOGRAPHY;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.BIOGRAPHY_BRIDE;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.BIOGRAPHY_GROOM;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.BLOG;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.ENTERTAINMENT;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.ENTERTAINMENT_LIGHTMUSIC;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.ENTERTAINMENT_SANGEETH;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.GALLERY;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.HOME;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.INVITATION;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.VENUE;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.VENUE_ENGAGEMENT;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.VENUE_RECEPTION;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.VENUE_WEDDING;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.getMenuGroupCompleteList;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.getMenuGroupHeaders;

/**
 * Created by Ayyappan on 03/11/2015.
 */
public class MenuDrawerActivity extends AppCompatActivity {

    //menu drawer
    ActionBarDrawerToggle drawerToggle;

    //expandable list view
    ExpandableListView drawerList;
    MenuDrawerListAdapter listAdapter;

    private int lastExpandedPosition = -1;

    protected void onCreateDrawer()
    {
        // R.id.drawer_layout should be in every activity with exactly the same id.
       // drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //find the drawer layout
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //find the expandable list drawer
        drawerList = (ExpandableListView) findViewById(R.id.left_drawer);

        //create expandable list adapter
        listAdapter = new MenuDrawerListAdapter(this, getMenuGroupHeaders(), getMenuGroupCompleteList());

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
                switch (groupName) {
                    case HOME:
                        startActivity(new Intent(MenuDrawerActivity.this, HomeActivity.class));
                        break;
                    case GALLERY:
                        startActivity(new Intent(MenuDrawerActivity.this, GalleryGridActivity.class));
                        break;
           /*         case BLOG:
                        startActivity(new Intent(MenuDrawerActivity.this, HomeActivity.class));
                        break;*/
                    case INVITATION:
                        startActivity(new Intent(MenuDrawerActivity.this, InvitationActivity.class));
                        break;
                    case ABOUT:
                        startActivity(new Intent(MenuDrawerActivity.this, AboutActivity.class));
                        break;
                    default:
                        return false;
                }

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
                switch (groupName) {
                    case BIOGRAPHY:
                        Intent intent1 = new Intent(MenuDrawerActivity.this, BiographyActivity.class);
                        switch (childName) {
                            case BIOGRAPHY_BRIDE:
                                intent1.putExtra("selected_option",0);
                                startActivity(intent1);
                                break;
                            case BIOGRAPHY_GROOM:
                                intent1.putExtra("selected_option",1);
                                startActivity(intent1);
                                break;
                        }
                        break;
                    case ENTERTAINMENT:
                        switch (childName) {
                            case ENTERTAINMENT_LIGHTMUSIC:
                                startActivity(new Intent(MenuDrawerActivity.this, LightMusicActivity.class));
                                break;
                            case ENTERTAINMENT_SANGEETH:
                                startActivity(new Intent(MenuDrawerActivity.this, HomeActivity.class));
                                break;
                        }
                        break;
                    case VENUE:
                        Intent intent = new Intent(MenuDrawerActivity.this, VenueActivity.class);

                        List<Venue> venues = new ArrayList<>();

                    //    venues.add(new Venue("Engagement", new DateTime("2016-02-10"),"Hotel Savera","Samvesh","146, Dr.Radhakrishnan Road","Mylapore, Chennai","Tamil Nadu 600004, India",13.045239, 80.261680));
                        venues.add(new Venue("Reception", new DateTime("2016-02-10").withHourOfDay(19).withMinuteOfHour(0).withSecondOfMinute(0), "Sadayappa", "Samvesh", "146, Dr.Radhakrishnan Road", "Mylapore, Chennai", "Tamil Nadu 600004, India", 13.045760, 80.071426));
                        venues.add(new Venue("Wedding", new DateTime("2016-02-10").withHourOfDay(19).withMinuteOfHour(0).withSecondOfMinute(0), "Sadayappa", "Samvesh", "146, Dr.Radhakrishnan Road", "Mylapore, Chennai", "Tamil Nadu 600004, India", 13.045760, 80.071426));

                        intent.putParcelableArrayListExtra("venues", (ArrayList<? extends Parcelable>) venues);

                        switch (childName) {
                            case VENUE_RECEPTION:
                                intent.putExtra("venue_position", 1);
                                startActivity(intent);
                                break;
                            case VENUE_WEDDING:
                                intent.putExtra("venue_position", 2);
                                startActivity(intent);
                                break;
                        }
                        break;
                    default:
                        return false;
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
