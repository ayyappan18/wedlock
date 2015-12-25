package com.ayyappan.androidapp.wedlock.biography;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.TestActivity;
import com.ayyappan.androidapp.wedlock.biography.bean.Bio;
import com.ayyappan.androidapp.wedlock.biography.bean.Couple;
import com.ayyappan.androidapp.wedlock.gallery.adapter.GalleryImageAdapter;
import com.ayyappan.androidapp.wedlock.gallery.bean.Image;
import com.ayyappan.androidapp.wedlock.gallery.data.Constants;
import com.ayyappan.androidapp.wedlock.gallery.data.MongoDB;
import com.ayyappan.androidapp.wedlock.home.BiographyDetailsDownloader;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.menudrawer.MenuDrawerActivity;
import com.ayyappan.androidapp.wedlock.biography.adapter.BiographyPagerAdapter;
import com.ayyappan.androidapp.wedlock.menudrawer.utils.IconDecoder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.decode.ImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.HashMap;
import java.util.List;

public class BiographyActivity extends MenuDrawerActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private BiographyPagerAdapter mSectionsPagerAdapter;

    private static Boolean firstTimeLoad = true;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static DisplayImageOptions options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biography);
        super.onCreateDrawer();

        mSectionsPagerAdapter = new BiographyPagerAdapter(getSupportFragmentManager());


        Integer sectionNumber = getIntent().getIntExtra("selected_option", 0);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(sectionNumber);

        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar);
        String title = "";
        if (sectionNumber == 0) title = "bride";
        else title = "groom";
        actionBar.setTitle(title);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

         /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
         public static PlaceholderFragment newInstance(int sectionNumber, String person) {
            PlaceholderFragment fragment = new PlaceholderFragment();

            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString("person", person);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment(){

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_biography, container, false);

            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getContext()));

            //  String person = getActivity().getIntent().getStringExtra("person");

            Integer sectionNumber = getArguments().getInt("section_number");

            Couple couple = new GlobalData(getContext()).getCouple();

            if (couple != null) {
                populateCoupleProfile(rootView, couple, sectionNumber);
            } else {
                new BiographyDetailsLocalDownloader(rootView,getContext(),sectionNumber).execute();
            }

            return rootView;

/*
                options = new DisplayImageOptions.Builder()
                        .showImageForEmptyUri(R.drawable.ic_empty)
                        .showImageOnFail(R.drawable.ic_error)
                        .resetViewBeforeLoading(true)
                        .cacheOnDisk(false)
                        .cacheInMemory(false)
                        .imageScaleType(ImageScaleType.EXACTLY)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .considerExifParams(true)
                        .displayer(new FadeInBitmapDisplayer(300))
                        .build();

                ImageLoader.getInstance().displayImage(bio.getPictureUrl(), imageView, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        //	spinner.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        String message = null;
                        switch (failReason.getType()) {
                            case IO_ERROR:
                                message = "Input/Output error";
                                break;
                            case DECODING_ERROR:
                                message = "Image can't be decoded";
                                break;
                            case NETWORK_DENIED:
                                message = "Downloads are denied";
                                break;
                            case OUT_OF_MEMORY:
                                message = "Out Of Memory error";
                                break;
                            case UNKNOWN:
                                message = "Unknown error";
                                break;
                        }
                        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

                        //	spinner.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
             //           imageView.setImageBitmap(loadedImage);
                        //	spinner.setVisibility(View.GONE);
                    }
                });

                //	photoView.setImageResource(sDrawables[position]);

                // Now just add PhotoView to ViewPager and return it
            //   container.addView(imageView);

            }
            return rootView;

        }

       //     return rootView;
      //  }*/
        }

        private void populateCoupleProfile(View rootView, Couple couple, Integer sectionNumber) {
            Bio bio;

            Bitmap pictureDrawable;
            HashMap<String,Bitmap> profilePictures = IconDecoder.getCoupleProfileBitMaps(getActivity().getApplicationContext());

            if (sectionNumber == 0) {
                pictureDrawable = profilePictures.get("bride");
                bio = couple.getBride();
            } else {
                pictureDrawable = profilePictures.get("groom");
                bio = couple.getGroom();
            }

            TextView nameText = (TextView) rootView.findViewById(R.id.name);
            nameText.setText(bio.getName());

            ImageView imageView = (ImageView) rootView.findViewById(R.id.picture);
            imageView.setImageBitmap(pictureDrawable);

            TextView bioText = (TextView) rootView.findViewById(R.id.bio);
            bioText.setText(bio.getBio());
        }

        private class BiographyDetailsLocalDownloader extends AsyncTask<Void, Void, Couple> {

            private View rootView;
            private Context context;
            private Integer sectionNumber;
            public BiographyDetailsLocalDownloader(View rootView, Context context, Integer sectionNumber){
                this.rootView = rootView;
                this.context = context;
                this.sectionNumber = sectionNumber;
            }

            @Override
            protected Couple doInBackground(Void... urls) {

                // params comes from the execute() call: params[0] is the url.
                return new MongoDB().getCoupleInfo();
            }

            // onPostExecute displays the results of the AsyncTask.
            @Override
            protected void onPostExecute(Couple couple) {
                Toast.makeText(context, "Couple profile details downloaded in Couple profile", Toast.LENGTH_SHORT).show();
                new GlobalData(context).setCouple(couple);
                populateCoupleProfile(rootView,couple,sectionNumber);
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        finish();
    }


}
