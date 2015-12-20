package com.ayyappan.androidapp.wedlock.venue;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.menudrawer.MenuDrawerActivity;
import com.ayyappan.androidapp.wedlock.venue.adapter.SectionsPagerAdapter;
import com.ayyappan.androidapp.wedlock.venue.bean.Venue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayyappan on 04/11/2015.
 */
public class VenueActivity extends MenuDrawerActivity {

    private static GoogleMap googleMap;
    public static FragmentManager fragmentManager;
    SupportMapFragment mMapFragment;

    private ViewPager viewPager;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);
        super.onCreateDrawer();


        ArrayList<Venue> venues = getIntent().getParcelableArrayListExtra("venues");
        Integer venuePosition = getIntent().getIntExtra("venue_position", 0);

        // Initializing view page
        viewPager = (ViewPager) findViewById(R.id.container);

        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getSupportFragmentManager();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), venues);

        //Create tab layout and register tab selection listener
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setOnTabSelectedListener(tabSelectionListener());

        //Add tabs
        for (Venue venue : venues) {
            tabLayout.addTab(tabLayout.newTab().setText(venue.getEventName()));
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //set current view pager based on user selection
        viewPager.setAdapter(mSectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(venuePosition);

    }

    private TabLayout.OnTabSelectedListener tabSelectionListener(){
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }
}
