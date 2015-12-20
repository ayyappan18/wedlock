package com.ayyappan.androidapp.wedlock;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Ayyappan on 19/12/2015.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("EuphoriaScript-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}