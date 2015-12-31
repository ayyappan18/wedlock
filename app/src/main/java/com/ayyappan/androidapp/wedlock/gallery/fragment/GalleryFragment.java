package com.ayyappan.androidapp.wedlock.gallery.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.gallery.adapter.GalleryImageAdapter;
import com.ayyappan.androidapp.wedlock.gallery.bean.Image;
import com.ayyappan.androidapp.wedlock.database.MongoDB;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class GalleryFragment extends Fragment {

    protected static AbsListView listView;

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    public GalleryFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fr_image_grid, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_fragment_gallery));

        if(ImageLoader.getInstance()==null)
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getContext()));
        listView = (GridView) rootView.findViewById(R.id.grid);

        GlobalData globalData = new GlobalData(getContext());
        String[] imageUrls = globalData.getImagesUrls();
        if( imageUrls == null || imageUrls.length ==0) {
            new DownloadImageUrls(getContext(),getFragmentManager()).execute();
        }
        else{
            ((GridView) listView).setAdapter(new GalleryImageAdapter(getContext(),imageUrls));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startImagePagerFragment(position,getChildFragmentManager());
                }
            });
        }

        return rootView;
    }


    protected static void startImagePagerFragment(int position, FragmentManager fragmentManager) {

     //   fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .add(R.id.content_fragment, GalleryImageViewFragment.newInstance(position))
                .addToBackStack("gallery")
                .commit();
       // listView.setVisibility(View.GONE);
      //  listView = null;
    }


    private static class DownloadImageUrls extends AsyncTask<Void, Void, String[]> {

        private Context context;
        private FragmentManager fragmentManager;

        public DownloadImageUrls(Context context,FragmentManager fragmentManager){
            this.context = context;
            this.fragmentManager = fragmentManager;
        }
        @Override
        protected String[] doInBackground(Void... urls) {
            List<Image> images = new MongoDB().getImages();
            String[] result = new String[images.size()];
            for(int i=0; i<images.size() ; i++){
                result[i] = images.get(i).getFullsizeUri();
            }
            images=null;
            return result;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String[] result) {
            //Constants.IMAGES = result;
            Toast.makeText(context, "Gallery urls are downloaded in Gallery..", Toast.LENGTH_SHORT).show();

            GlobalData globalData = new GlobalData(context);
            globalData.setImagesUrls(result);
            ((GridView) listView).setAdapter(new GalleryImageAdapter(context,globalData.getImagesUrls()));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startImagePagerFragment(position,fragmentManager);
                }
            });

        }
    }
}
