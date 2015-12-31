package com.ayyappan.androidapp.wedlock.home;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.biography.bean.Couple;
import com.ayyappan.androidapp.wedlock.database.MongoDB;

/**
 * Created by Ayyappan on 05/12/2015.
 */
public class BiographyDetailsDownloader extends AsyncTask<Void, Void, Couple> {

    private Context mContext;
    public BiographyDetailsDownloader (Context context){
        mContext = context;
    }

    @Override
    protected Couple doInBackground(Void... urls) {

        // params comes from the execute() call: params[0] is the url.
       return new MongoDB().getCoupleInfo();
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(Couple result) {
        Toast.makeText(mContext, "Couple profile details are downloaded.", Toast.LENGTH_SHORT).show();

        new GlobalData(mContext).setCouple(result);
    }
}