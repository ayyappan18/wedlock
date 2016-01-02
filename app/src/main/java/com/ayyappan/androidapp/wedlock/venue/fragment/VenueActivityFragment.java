package com.ayyappan.androidapp.wedlock.venue.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.venue.adapter.SectionsPagerAdapter;
import com.ayyappan.androidapp.wedlock.model.Venue;

import java.util.List;

public class VenueActivityFragment extends Fragment {

    private static List<Venue> venues = null;
    private static final String EVENT_POSITION = "event-position";
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static FragmentManager fragmentManager;

    private ViewPager viewPager;
    public static VenueActivityFragment newInstance(List<Venue> venuesList, int position) {
        VenueActivityFragment fragment = new VenueActivityFragment();
        venues = venuesList;
        Bundle bundle = new Bundle();
        bundle.putInt(EVENT_POSITION,position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public VenueActivityFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_venue, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.title_fragment_event));

        // for refreshing UI
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_fragment_event));

        // Initializing view page
        viewPager = (ViewPager) rootView.findViewById(R.id.container);

        // initialising the object of the FragmentManager. Here I'm passing getSupportFragmentManager(). You can pass getFragmentManager() if you are coding for Android 3.0 or above.
        fragmentManager = getFragmentManager();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), venues);

        //Create tab layout and register tab selection listener
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.setOnTabSelectedListener(tabSelectionListener());

        //Add tabs
        for (Venue venue : venues) {
            tabLayout.addTab(tabLayout.newTab().setText(venue.getEventName()));
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //set current view pager based on user selection
        viewPager.setAdapter(mSectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setCurrentItem(getArguments().getInt(EVENT_POSITION,0));

        return rootView;
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
