package com.ayyappan.androidapp.wedlock.childfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.adapters.BiographyPagerAdapter;

public class BiographyFragment extends Fragment {

    private BiographyPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public static BiographyFragment newInstance(Integer selectedOption) {
        BiographyFragment fragment = new BiographyFragment();
        Bundle args = new Bundle();
        args.putInt("selected_option", selectedOption);
        fragment.setArguments(args);
        return fragment;
    }

    public BiographyFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_biography, container, false);

        mSectionsPagerAdapter = new BiographyPagerAdapter(getFragmentManager());

        Integer sectionNumber = getArguments().getInt("selected_option", 0);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(sectionNumber);

        return rootView;
    }

}
