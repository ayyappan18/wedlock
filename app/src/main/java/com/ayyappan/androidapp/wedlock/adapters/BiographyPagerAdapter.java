package com.ayyappan.androidapp.wedlock.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ayyappan.androidapp.wedlock.fragments.CoupleProfileFragment;

/**
 * Created by Ayyappan on 05/12/2015.
 */

public class BiographyPagerAdapter extends FragmentPagerAdapter {


    public BiographyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return CoupleProfileFragment.PlaceholderFragment.newInstance(position, "bride");
            case 1 : return CoupleProfileFragment.PlaceholderFragment.newInstance(position, "groom");
            default: return null;
        }
    }

    @Override
    public int getCount() {
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
