package com.ayyappan.androidapp.wedlock.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayyappan.androidapp.wedlock.R;

public class SangeethFragment extends Fragment {

    public static SangeethFragment newInstance() {
        SangeethFragment fragment = new SangeethFragment();
        return fragment;
    }

    public SangeethFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sangeeth, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.title_fragment_sangeeth));

        // for refreshing UI
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
      //  ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_fragment_about));

        return rootView;
    }

}
