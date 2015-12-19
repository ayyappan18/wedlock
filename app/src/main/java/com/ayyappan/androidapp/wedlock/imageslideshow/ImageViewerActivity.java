package com.ayyappan.androidapp.wedlock.imageslideshow;

import android.os.Bundle;
import android.view.Menu;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.imageslideshow.fragment.ImageViewerFragment;

/**
 * Created by Ayyappan on 31/10/2015.
 */
public class ImageViewerActivity extends FragmentActivity {

    private Fragment contentFragment;
    ImageViewerFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
            }
            if (fragmentManager.findFragmentByTag(ImageViewerFragment.ARG_ITEM_ID) != null) {
                homeFragment = (ImageViewerFragment) fragmentManager
                        .findFragmentByTag(ImageViewerFragment.ARG_ITEM_ID);
                contentFragment = homeFragment;
            }
        } else {
            homeFragment = new ImageViewerFragment();
            switchContent(homeFragment, ImageViewerFragment.ARG_ITEM_ID);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof ImageViewerFragment) {
            outState.putString("content", ImageViewerFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }

    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            // Only ProductDetailFragment is added to the back stack.
            if (!(fragment instanceof ImageViewerFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof ImageViewerFragment
                || fm.getBackStackEntryCount() == 0) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }
}
