package com.ayyappan.androidapp.wedlock.gallery.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.gallery.data.Constants;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import uk.co.senab.photoview.PhotoView;

public class GalleryImageViewFragment extends Fragment {

    private static ViewPager mViewPager;
    private static DisplayImageOptions options;

    public static GalleryImageViewFragment newInstance(Integer initialImagePosition) {
        GalleryImageViewFragment fragment = new GalleryImageViewFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.Extra.IMAGE_POSITION, initialImagePosition);
        fragment.setArguments(args);
        return fragment;
    }

    public GalleryImageViewFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_view_pager_gallery, container, false);
        mViewPager = (HackyViewPager) rootView.findViewById(R.id.view_pager);

        mViewPager.setBackgroundColor(Color.BLACK);
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        mViewPager.setAdapter(new SamplePagerAdapter(getChildFragmentManager(), getContext()));
        mViewPager.setCurrentItem(getArguments().getInt(Constants.Extra.IMAGE_POSITION, 0));

        rootView.requestFocus();

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });

        return rootView;
    }

    private static class SamplePagerAdapter extends FragmentPagerAdapter {

        private static String[] IMAGE_URLS = null;

        public SamplePagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            IMAGE_URLS = new GlobalData(context).getImagesUrls();
        }

        /*private static final int[] sDrawables = { R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper,
                R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper };
*/
        @Override
        public int getCount() {
            return IMAGE_URLS.length;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return GalleryImageViewFragment.PlaceholderFragment.newInstance(IMAGE_URLS[position],position);
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String IMAGE_URL = "image-url";

        public static PlaceholderFragment newInstance(String imageUrl, int imagePosition) {
            PlaceholderFragment fragment = new PlaceholderFragment();

            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, imagePosition);
            args.putString(IMAGE_URL, imageUrl);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final PhotoView photoView = new PhotoView(container.getContext());

            ImageLoader.getInstance().displayImage(getArguments().getString(IMAGE_URL), photoView, options, new SimpleImageLoadingListener() {
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
                    photoView.setImageBitmap(loadedImage);
                    //	spinner.setVisibility(View.GONE);
                }
            });

            //	photoView.setImageResource(sDrawables[position]);

            // Now just add PhotoView to ViewPager and return it

            // container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


            return photoView;
        }
    }
}


