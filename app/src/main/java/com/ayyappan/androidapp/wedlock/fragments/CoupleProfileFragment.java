package com.ayyappan.androidapp.wedlock.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.adapters.BiographyPagerAdapter;
import com.ayyappan.androidapp.wedlock.model.Bio;
import com.ayyappan.androidapp.wedlock.model.Couple;
import com.ayyappan.androidapp.wedlock.database.MongoDB;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.utils.CheckNetwork;
import com.ayyappan.androidapp.wedlock.utils.CoupleProfileJsonReader;
import com.ayyappan.androidapp.wedlock.utils.IconDecoder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;

import java.util.HashMap;

public class CoupleProfileFragment extends Fragment {

    private static ViewPager mViewPager;
    private static BiographyPagerAdapter mSectionsPagerAdapter;
    private static final String PROFILE_SELECTION = "selected-profile";

    public static CoupleProfileFragment newInstance(int selectedProfile) {
        CoupleProfileFragment fragment = new CoupleProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PROFILE_SELECTION, selectedProfile);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CoupleProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.biography_fragment, container, false);
        mSectionsPagerAdapter = new BiographyPagerAdapter(getChildFragmentManager());

        Integer sectionNumber = getArguments().getInt(PROFILE_SELECTION, 0);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(sectionNumber);

        return rootView;
    }

    /**
     * Couple fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber, String person) {
            PlaceholderFragment fragment = new PlaceholderFragment();

            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString("person", person);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_biography, container, false);

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            actionBar.setTitle("Couple");

            if (ImageLoader.getInstance() == null)
                ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getContext()));

            Integer sectionNumber = getArguments().getInt("section_number");

            Couple couple = new GlobalData(getContext()).getCouple();

            if (couple == null) {
                CheckNetwork checkNetwork = new CheckNetwork();
                if (checkNetwork.isConnected(getContext()))
                    new BiographyDetailsLocalDownloader(rootView, getContext(), sectionNumber).execute();
                else {
                    try {
                        Couple offlineCoupleDetails = new CoupleProfileJsonReader().getCouple(getContext());
                        populateCoupleProfile(rootView, getContext(), offlineCoupleDetails, sectionNumber, false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                populateCoupleProfile(rootView, getContext(), couple, sectionNumber, false);
            }

            return rootView;
        }

        public static void populateCoupleProfile(View rootView, Context context, Couple couple, Integer sectionNumber, boolean isOnline) {
            Bio bio;

            int pictureResource;

            if (sectionNumber == 0) {
                pictureResource = couple.getBride().getPictureResourceId();
                bio = couple.getBride();
            } else {
                pictureResource = couple.getGroom().getPictureResourceId();
                bio = couple.getGroom();
            }

            TextView nameText = (TextView) rootView.findViewById(R.id.name);
            nameText.setText(bio.getName());

            TextView bioText = (TextView) rootView.findViewById(R.id.bio);
            bioText.setText(bio.getBio());

            ImageView imageView = (ImageView) rootView.findViewById(R.id.picture);
            imageView.setImageResource(pictureResource);

        }
    }

    private static class BiographyDetailsLocalDownloader extends AsyncTask<Void, Void, Couple> {

        private View rootView;
        private Context context;
        private Integer sectionNumber;

        public BiographyDetailsLocalDownloader(View rootView, Context context, Integer sectionNumber) {
            this.rootView = rootView;
            this.context = context;
            this.sectionNumber = sectionNumber;
        }

        @Override
        protected Couple doInBackground(Void... urls) {
            try {
                return new MongoDB().getCoupleInfo(context);
            } catch (Exception ex) {
                System.out.println(ex);
                return null;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Couple couple) {
            if (couple == null) {
                CoupleProfileJsonReader localCouple = new CoupleProfileJsonReader();
                PlaceholderFragment.populateCoupleProfile(rootView, context, couple, sectionNumber, true);
            } else {
                Toast.makeText(context, "Couple profile details downloaded in Couple profile", Toast.LENGTH_SHORT).show();
                new GlobalData(context).setCouple(couple);
                PlaceholderFragment.populateCoupleProfile(rootView, context, couple, sectionNumber, true);
            }
        }
    }
}
