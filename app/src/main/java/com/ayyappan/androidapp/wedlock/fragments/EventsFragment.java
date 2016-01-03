package com.ayyappan.androidapp.wedlock.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.adapters.EventsPagerAdapter;
import com.ayyappan.androidapp.wedlock.model.Venue;

import java.util.List;

public class EventsFragment extends Fragment {

    private static List<Venue> venues = null;
    private static final String EVENT_POSITION = "event-position";
    private EventsPagerAdapter mEventsPagerAdapter;
    private static ViewPager viewPager;

    public static EventsFragment newInstance(List<Venue> venuesList, int position) {
        EventsFragment fragment = new EventsFragment();
        venues = venuesList;
        Bundle bundle = new Bundle();
        bundle.putInt(EVENT_POSITION,position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public EventsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.title_fragment_event));

        // Initializing view page
        viewPager = (ViewPager) rootView.findViewById(R.id.container);

        mEventsPagerAdapter = new EventsPagerAdapter(getChildFragmentManager(), venues);

        //Create tab layout and register tab selection listener
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.setOnTabSelectedListener(tabSelectionListener());

        //Add tabs
        for (Venue venue : venues) {
            tabLayout.addTab(tabLayout.newTab().setText(venue.getEventName()));
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //set current view pager based on user selection
        viewPager.setAdapter(mEventsPagerAdapter);
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
