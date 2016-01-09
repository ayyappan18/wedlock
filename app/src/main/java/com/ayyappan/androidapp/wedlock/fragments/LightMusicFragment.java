package com.ayyappan.androidapp.wedlock.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.activities.GalleryViewPagerActivity;
import com.ayyappan.androidapp.wedlock.activities.SongRequestActivity;
import com.ayyappan.androidapp.wedlock.model.Constants;

public class LightMusicFragment extends Fragment {

    public static LightMusicFragment newInstance() {
        LightMusicFragment fragment = new LightMusicFragment();
        return fragment;
    }

    public LightMusicFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_light_music, container, false);

        final RelativeLayout layout = (RelativeLayout) rootView.findViewById(R.id.light_music_layout);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.title_fragment_light_music));

        Button button = (Button) rootView.findViewById(R.id.express_song_interest_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SongRequestActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
