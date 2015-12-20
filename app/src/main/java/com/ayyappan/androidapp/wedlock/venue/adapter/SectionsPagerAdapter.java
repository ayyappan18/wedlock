package com.ayyappan.androidapp.wedlock.venue.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ayyappan.androidapp.wedlock.venue.VenueActivity;
import com.ayyappan.androidapp.wedlock.venue.bean.Venue;
import com.ayyappan.androidapp.wedlock.venue.fragment.VenueFragment;

import java.util.List;

import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.EVENTS;
import static com.ayyappan.androidapp.wedlock.menudrawer.data.MenuOptions.getMenuGroupCompleteList;

/**
 * Created by Ayyappan on 05/11/2015.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public static List<Venue> venues;
    public SectionsPagerAdapter(FragmentManager fm, List<Venue> venues) {
        super(fm);
        this.venues = venues;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return VenueFragment.newInstance(position,venues.get(position));
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return venues.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String childName = getMenuGroupCompleteList().get(EVENTS).get(position);

        return childName;
    }
}