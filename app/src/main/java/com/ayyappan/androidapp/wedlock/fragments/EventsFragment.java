package com.ayyappan.androidapp.wedlock.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.adapters.EventsPagerAdapter;
import com.ayyappan.androidapp.wedlock.adapters.SlidingTabLayout;
import com.ayyappan.androidapp.wedlock.childfragments.EventDetailsFragment;
import com.ayyappan.androidapp.wedlock.model.Venue;

import java.util.List;

public class EventsFragment extends Fragment {

    private static List<Venue> venues = null;
    private static final String EVENT_POSITION = "event-position";
    private EventsPagerAdapter mEventsPagerAdapter;
    private static ViewPager viewPager;
    SlidingTabLayout mSlidingTabLayout;

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

        viewPager = (ViewPager) rootView.findViewById(R.id.container);

        mEventsPagerAdapter = new EventsPagerAdapter(getChildFragmentManager(), venues);

        //Create tab layout and register tab selection listener
      //  tabLayout.setup(getContext(), getChildFragmentManager(), android.R.id.);

        //     tabLayout.setOnTabSelectedListener(tabSelectionListener());
        //set current view pager based on user selection
        viewPager.setAdapter(mEventsPagerAdapter);

        mSlidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.PURPLE_BUTTON));
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return R.color.white;
            }
        });
        mSlidingTabLayout.setViewPager(viewPager);

      /*  //Add tabs
        for (Venue venue : venues) {
            tabLayout.addTab(
                    tabLayout.newTabSpec(venue.getEventName()).setIndicator(venue.getEventName(), null),
                    EventDetailsFragment.class, null);

       //     tabLayout.addTab(tabLayout.newTab().setText(venue.getEventName()));
        }*/

   //     tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


    //    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setCurrentItem(getArguments().getInt(EVENT_POSITION,0));

        return rootView;
    }

  /*  private TabLayout.OnTabSelectedListener tabSelectionListener(){
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
*/
}
