package com.ayyappan.androidapp.wedlock.biography.adapter;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ayyappan.androidapp.wedlock.biography.BiographyActivity;

import java.util.HashMap;

/**
 * Created by Ayyappan on 05/12/2015.
 */

public class BiographyPagerAdapter extends FragmentPagerAdapter {


    public BiographyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){
            case 0: return BiographyActivity.PlaceholderFragment.newInstance(position, "bride");
            case 1 : return BiographyActivity.PlaceholderFragment.newInstance(position, "groom");
            default: return null;
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Bride";
            case 1:
                return "Groom";
        }
        return null;
    }
}
