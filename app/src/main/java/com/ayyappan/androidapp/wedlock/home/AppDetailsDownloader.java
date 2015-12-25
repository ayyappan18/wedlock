package com.ayyappan.androidapp.wedlock.home;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.biography.bean.Couple;
import com.ayyappan.androidapp.wedlock.gallery.bean.Image;
import com.ayyappan.androidapp.wedlock.gallery.data.MongoDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayyappan on 05/12/2015.
 */
public class AppDetailsDownloader extends AsyncTask<Void, Void, AppData> {

    private Context mContext;
    public AppDetailsDownloader(Context context){
        mContext = context;
    }

    @Override
    protected AppData doInBackground(Void... urls) {

        // params comes from the execute() call: params[0] is the url.
       return new MongoDB().getAppData();
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(AppData result) {
        Toast.makeText(mContext, "App all data are downloaded.", Toast.LENGTH_SHORT).show();

        GlobalData globalData = new GlobalData(mContext);
        globalData.setCouple(result.getCouple());

        List<Image> imageList = result.getImages();
        String[] images = new String[imageList.size()];

        for(int i=0; i<imageList.size() ; i++){
            images[i] = imageList.get(i).getFullsizeUri();
        }
        globalData.setImagesUrls(images);
    }
}