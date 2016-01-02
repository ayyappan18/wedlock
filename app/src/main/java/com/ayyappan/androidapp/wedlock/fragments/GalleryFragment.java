package com.ayyappan.androidapp.wedlock.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.adapters.GalleryGridAdapter;
import com.ayyappan.androidapp.wedlock.model.Image;
import com.ayyappan.androidapp.wedlock.database.MongoDB;
import com.ayyappan.androidapp.wedlock.model.Constants;
import com.ayyappan.androidapp.wedlock.activities.GalleryViewPagerActivity;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.utils.CheckNetwork;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class GalleryFragment extends Fragment {

    protected static AbsListView listView;
    protected static TextView noInternetTextView;

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    public GalleryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_grid_gallery, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.title_fragment_gallery));

        // for refreshing UI
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_fragment_gallery));


        if (ImageLoader.getInstance() == null)
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getContext()));

        listView = (GridView) rootView.findViewById(R.id.grid);
        noInternetTextView = (TextView) rootView.findViewById(R.id.no_internet_gallery);

        CheckNetwork checkNetwork = new CheckNetwork();
        if (checkNetwork.isConnected(getContext())) {
            noInternetTextView.setVisibility(View.GONE);
            GlobalData globalData = new GlobalData(getContext());
            String[] imageUrls = globalData.getImagesUrls();
            if (imageUrls == null || imageUrls.length == 0) {
                new DownloadImageUrls(getContext(), getFragmentManager()).execute();
            } else {
                ((GridView) listView).setAdapter(new GalleryGridAdapter(getContext(), imageUrls));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startImagePagerFragment(position, getChildFragmentManager());
                    }
                });
            }
        } else {
            listView.setVisibility(View.GONE);
        }

        return rootView;
    }

    protected void startImagePagerFragment(int position, FragmentManager fragmentManager) {
        Intent intent = new Intent(getActivity(), GalleryViewPagerActivity.class);
        intent.putExtra(Constants.Extra.IMAGE_POSITION, position);
        startActivity(intent);
    }


    private class DownloadImageUrls extends AsyncTask<Void, Void, String[]> {

        private Context context;
        private FragmentManager fragmentManager;

        public DownloadImageUrls(Context context, FragmentManager fragmentManager) {
            this.context = context;
            this.fragmentManager = fragmentManager;
        }

        @Override
        protected String[] doInBackground(Void... urls) {
            List<Image> images = new MongoDB().getImages();
            String[] result = new String[images.size()];
            for (int i = 0; i < images.size(); i++) {
                result[i] = images.get(i).getFullsizeUri();
            }
            images = null;
            return result;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String[] result) {
            //Constants.IMAGES = result;
            Toast.makeText(context, "Gallery urls are downloaded in Gallery..", Toast.LENGTH_SHORT).show();

            GlobalData globalData = new GlobalData(context);
            globalData.setImagesUrls(result);
            ((GridView) listView).setAdapter(new GalleryGridAdapter(context, globalData.getImagesUrls()));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startImagePagerFragment(position, fragmentManager);
                }
            });

        }
    }
}
