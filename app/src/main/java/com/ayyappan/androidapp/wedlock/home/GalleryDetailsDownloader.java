package com.ayyappan.androidapp.wedlock.home;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.gallery.bean.Image;
import com.ayyappan.androidapp.wedlock.database.MongoDB;

import java.util.List;

/**
 * Created by Ayyappan on 05/12/2015.
 */
public class GalleryDetailsDownloader extends AsyncTask<Void, Void, String[]> {

    private Context mContext;
    public GalleryDetailsDownloader (Context context){
        mContext = context;
    }

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
        Toast.makeText(mContext, "Gallery urls are downloaded.", Toast.LENGTH_SHORT).show();

        new GlobalData(mContext).setImagesUrls(result);
    }
}