package com.ayyappan.androidapp.wedlock.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ayyappan.androidapp.wedlock.model.Venue;
import com.ayyappan.androidapp.wedlock.childfragments.EventDetailsFragment;

import java.util.List;

import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.EVENTS;
import static com.ayyappan.androidapp.wedlock.utils.MenuOptions.getMenuGroupCompleteList;

/**
 * Created by Ayyappan on 05/11/2015.
 */
public class EventsPagerAdapter extends FragmentPagerAdapter {

    public static List<Venue> venues;
    public EventsPagerAdapter(FragmentManager fm, List<Venue> venues) {
        super(fm);
        this.venues = venues;
    }

    @Override
    public Fragment getItem(int position) {
        return EventDetailsFragment.newInstance(position, venues.get(position));
    }

    @Override
    public int getCount() {
        return venues.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String childName = getMenuGroupCompleteList().get(EVENTS).get(position);
        return childName;
    }
}