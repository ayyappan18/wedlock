package com.ayyappan.androidapp.wedlock.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayyappan.androidapp.wedlock.database.mongolab.GetAppDetailsAsyncTask;
import com.ayyappan.androidapp.wedlock.fragments.AboutFragment;
import com.ayyappan.androidapp.wedlock.HomeFragment;
import com.ayyappan.androidapp.wedlock.fragments.CoupleProfileFragment;
import com.ayyappan.androidapp.wedlock.fragments.InvitationFragment;
import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.UILApplication;
import com.ayyappan.androidapp.wedlock.fragments.GalleryFragment;
import com.ayyappan.androidapp.wedlock.fragments.LightMusicFragment;
import com.ayyappan.androidapp.wedlock.fragments.RSVPFragment;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.utils.CheckNetwork;
import com.ayyappan.androidapp.wedlock.adapters.MenuDrawerListAdapter;
import com.ayyappan.androidapp.wedlock.model.Venue;
import com.ayyappan.androidapp.wedlock.fragments.EventsFragment;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.ABOUT;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.BIOGRAPHY;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.BIOGRAPHY_BRIDE;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.BIOGRAPHY_GROOM;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.ENTERTAINMENT;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.ENTERTAINMENT_LIGHTMUSIC;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.EVENTS;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.EVENT_RECEPTION;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.EVENT_WEDDING;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.GALLERY;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.HOME;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.INVITATION;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.RSVP;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.getMenuGroupCompleteList;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.getMenuGroupHeaders;

public class ApplicationActivity extends AppCompatActivity {
    //menu drawer
    private ActionBarDrawerToggle drawerToggle;

    //expandable list view
    private ExpandableListView drawerList;
    private static MenuDrawerListAdapter listAdapter = null;
    private int lastExpandedPosition = -1;
    private FragmentManager fragmentManager;
    private TextView txtWelcomeText;
    UILApplication app;
    RelativeLayout backgroundLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

     /*   NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.menu_header);*/

        txtWelcomeText = (TextView) findViewById(R.id.welcome_name);
        txtWelcomeText.setText(new GlobalData(getApplicationContext()).getUser().getName());
      /*  app = (UILApplication) getApplication();

        //Set background Image
        backgroundLayout = (RelativeLayout) findViewById(R.id.iv_background);
        if(backgroundLayout!=null) {
            app.setBackground(backgroundLayout, R.drawable.app_bg);
        }*/

        CheckNetwork checkNetwork = new CheckNetwork();

        if (checkNetwork.isConnected(getApplicationContext())) {
         //   final DBHelper localDB = new DBHelper(getApplicationContext());
            new GetAppDetailsAsyncTask(getApplicationContext()).execute();
        }

        //Set Menu drawerlist background
        drawerList = (ExpandableListView) findViewById(R.id.left_drawer);
     /*   Bitmap menuBackgroundBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.menu_bg);
        Drawable menuBg = new BitmapDrawable(getResources(), menuBackgroundBitMap);
        drawerList.setBackground(menuBg);*/
        // drawerList.setBackgroundDrawable(new BitmapDrawable(menuBackgroundBitMap));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //find the drawer layout
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //create expandable list adapter
        if (listAdapter == null)
            listAdapter = new MenuDrawerListAdapter(this, getMenuGroupHeaders(), getMenuGroupCompleteList());

        //initialise expandable list view with expandable list adapter
        drawerList.setAdapter(listAdapter);

        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //Initalise with home fragment
        fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0)
            fragmentManager.popBackStack();
        else
            fragmentManager.beginTransaction()
                    .replace(R.id.content_fragment, HomeFragment.newInstance())
                    .commit();

        //Replace fragment based on group header selection
        drawerList.setOnGroupClickListener(groupHeaderNavigation());
        drawerList.setOnChildClickListener(childNavigation());

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

    private ExpandableListView.OnGroupClickListener groupHeaderNavigation() {
        return new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                String groupName = getMenuGroupHeaders().get(groupPosition);
                fragmentManager.popBackStack();
                switch (groupName) {
                    case HOME:
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_fragment, HomeFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case INVITATION:
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_fragment, InvitationFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case GALLERY:
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_fragment, GalleryFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case RSVP:
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_fragment, RSVPFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case ABOUT:
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_fragment, AboutFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    default:
                        return false;
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    private ExpandableListView.OnChildClickListener childNavigation() {
        return new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String groupName = getMenuGroupHeaders().get(groupPosition);
                String childName = getMenuGroupCompleteList().get(groupName).get(childPosition);

                fragmentManager.popBackStack();

                switch (groupName) {
                    case BIOGRAPHY:
                        switch (childName) {
                            case BIOGRAPHY_BRIDE:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_fragment, CoupleProfileFragment.newInstance(0))
                                        .addToBackStack(null)
                                        .commit();
                                break;
                            case BIOGRAPHY_GROOM:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_fragment, CoupleProfileFragment.newInstance(1))
                                        .addToBackStack(null)
                                        .commit();
                                break;
                        }
                        break;
                    case ENTERTAINMENT:
                        switch (childName) {
                            case ENTERTAINMENT_LIGHTMUSIC:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_fragment, LightMusicFragment.newInstance())
                                        .addToBackStack(null)
                                        .commit();
                                break;
                        }
                        break;
                    case EVENTS:
                        List<Venue> venues = new GlobalData(getApplicationContext()).getVenue();
                        switch (childName) {
                            case EVENT_RECEPTION:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_fragment, EventsFragment.newInstance(venues, 0))
                                        .addToBackStack(null)
                                        .commit();
                                break;
                            case EVENT_WEDDING:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_fragment, EventsFragment.newInstance(venues, 1))
                                        .addToBackStack(null)
                                        .commit();
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
        };
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

/*    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // super.onBackPressed();
        }
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        clearBackStack();
    }
}
