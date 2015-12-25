/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.ayyappan.androidapp.wedlock.gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.R;

import com.ayyappan.androidapp.wedlock.database.googledrive.GoogleSpreadsheetDataSource;
import com.ayyappan.androidapp.wedlock.gallery.adapter.GalleryImageAdapter;
import com.ayyappan.androidapp.wedlock.gallery.bean.Image;
import com.ayyappan.androidapp.wedlock.gallery.data.Constants;
import com.ayyappan.androidapp.wedlock.gallery.data.MongoDB;
import com.ayyappan.androidapp.wedlock.gallery.fragment.ImagePagerFragment;
import com.ayyappan.androidapp.wedlock.gallery.fragment.ViewPagerActivity;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.menudrawer.MenuDrawerActivity;
import com.google.gdata.util.ServiceException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class GalleryGridActivity extends MenuDrawerActivity {

	public static final int INDEX = 1;

	protected static final String STATE_PAUSE_ON_SCROLL = "STATE_PAUSE_ON_SCROLL";
	protected static final String STATE_PAUSE_ON_FLING = "STATE_PAUSE_ON_FLING";

	protected AbsListView listView;

	protected boolean pauseOnScroll = false;
	protected boolean pauseOnFling = true;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		onCreateDrawer();

		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
		listView = (GridView) findViewById(R.id.grid);

		GlobalData globalData = new GlobalData(getApplicationContext());
		String[] imageUrls = globalData.getImagesUrls();
 		if( imageUrls == null || imageUrls.length ==0) {
			new DownloadImageUrls().execute();
		}
		else{
			((GridView) listView).setAdapter(new GalleryImageAdapter(getApplicationContext(),imageUrls));
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					startImagePagerActivity(position);
				}
			});
		}
	}


	@Override
	public void onResume() {
		super.onResume();
		applyScrollListener();
	}

	private void applyScrollListener() {
		listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll, pauseOnFling));
	}

	protected void startImagePagerActivity(int position) {
		Intent intent = new Intent(this, ViewPagerActivity.class);
		intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImagePagerFragment.INDEX);
		intent.putExtra(Constants.Extra.IMAGE_POSITION, position);
		startActivity(intent);
	}

	private class DownloadImageUrls extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... urls) {

			// params comes from the execute() call: params[0] is the url.
			List<Image> images = new MongoDB().getImages();
			String[] result = new String[images.size()];

			for(int i=0; i<images.size() ; i++){
				result[i] = images.get(i).getFullsizeUri();
			}

			return result;
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String[] result) {
			//Constants.IMAGES = result;
			Toast.makeText(getApplicationContext(), "Gallery urls are downloaded in Gallery..", Toast.LENGTH_SHORT).show();

			GlobalData globalData = new GlobalData(getApplicationContext());
			globalData.setImagesUrls(result);
			((GridView) listView).setAdapter(new GalleryImageAdapter(getApplicationContext(),globalData.getImagesUrls()));
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					startImagePagerActivity(position);
				}
			});

		}
	}

	@Override
	public void onDestroy(){
		super.onDestroy();

	}
}