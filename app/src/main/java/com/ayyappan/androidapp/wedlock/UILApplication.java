/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
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
package com.ayyappan.androidapp.wedlock;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ayyappan.androidapp.wedlock.R;
import com.ayyappan.androidapp.wedlock.gallery.data.Constants;
import com.ayyappan.androidapp.wedlock.menudrawer.utils.IconDecoder;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.Collection;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class UILApplication extends Application {

	private RelativeLayout bgimg; // layout of the activity
	private static Bitmap background; // background in the Bitmap format
	public BitmapDrawable bg; // background in the Drawable format
	private Bitmap menuBackgroundBitMap;
	private BitmapDrawable menuBackground;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		if (Constants.Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
		}

		super.onCreate();
		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
						.setDefaultFontPath("EuphoriaScript-Regular.ttf")
						.setFontAttrId(R.attr.fontPath)
						.build()
		);
		initImageLoader(getApplicationContext());


	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.memoryCacheSize(20 * 1024 * 1024);
		config.memoryCache(new UsingFreqLimitedMemoryCache(20 * 1024 * 1024));

		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}

	public void loadBackground(int id) {

		if(background == null) {
			//Background image
			WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
			final DisplayMetrics displayMetrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(displayMetrics);
			int height = displayMetrics.heightPixels;
			int width = displayMetrics.widthPixels;
			background = IconDecoder.decodeSampledBitmapFromResource(getResources(), R.drawable.app_bg, width, height);
		}
		bg = new BitmapDrawable(background);

		bgimg.setBackgroundDrawable(bg);
	}
	public void unloadBackground() {
		if (bgimg != null)
			bgimg.setBackgroundDrawable(null);
		if (bg!= null) {
			background.recycle();
			background = null;
		}
		bg = null;
	}

	public void setBackground(RelativeLayout i, int sourceid) {
		unloadBackground();
		bgimg = i;
		loadBackground(sourceid);
	}

	public BitmapDrawable getMenuBackground(){
		if(menuBackgroundBitMap == null)
			menuBackgroundBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.menu_bg);
		return menuBackground = new BitmapDrawable(menuBackgroundBitMap);
	}
}