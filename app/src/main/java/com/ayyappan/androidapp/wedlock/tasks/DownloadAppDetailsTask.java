package com.ayyappan.androidapp.wedlock.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.database.mongolab.GetCoupleProfileAsyncTask;
import com.ayyappan.androidapp.wedlock.model.Image;
import com.ayyappan.androidapp.wedlock.database.MongoDB;
import com.ayyappan.androidapp.wedlock.model.AppData;
import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.utils.CheckNetwork;

import java.util.List;

/**
 * Created by Ayyappan on 05/12/2015.
 */
public class DownloadAppDetailsTask extends AsyncTask<Void, Void, AppData> {

    private Context mContext;

    public DownloadAppDetailsTask(Context context) {
        mContext = context;
    }

    @Override
    protected AppData doInBackground(Void... urls) {

        // params comes from the execute() call: params[0] is the url.
        CheckNetwork checkNetwork = new CheckNetwork();
        if (checkNetwork.isOnline(mContext))
            try {
                return new MongoDB().getAppData(mContext);

            }catch(Exception ex){
                System.out.println(ex);
                return null;
            }
        else
            return null;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(AppData result) {
        if(result != null) {
            Toast.makeText(mContext, "App all data are downloaded.", Toast.LENGTH_SHORT).show();

            GlobalData globalData = new GlobalData(mContext);
            globalData.setCouple(result.getCouple());

            List<Image> imageList = result.getImages();
            String[] images = new String[imageList.size()];

            for (int i = 0; i < imageList.size(); i++) {
                images[i] = imageList.get(i).getFullsizeUri();
            }
            globalData.setImagesUrls(images);
        }
    }
}