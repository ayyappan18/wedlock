package com.ayyappan.androidapp.wedlock.database.mongolab;

import android.os.AsyncTask;

import com.ayyappan.androidapp.wedlock.model.Couple;
import com.ayyappan.androidapp.wedlock.model.Song;
import com.ayyappan.androidapp.wedlock.model.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Ayyappan on 03/01/2016.
 */
public class PostSongDetailsAsyncTask extends AsyncTask<Song, Void, Boolean> {

    public interface AsyncResponse {
        void processFinish(Boolean result);
    }

    public AsyncResponse delegate = null;

    public PostSongDetailsAsyncTask(AsyncResponse delegate){
    this.delegate = delegate;
    }

    @Override
    protected Boolean doInBackground(Song... arg0) {
        Song song = arg0[0];
        try {

            QueryBuilder qb = new QueryBuilder();

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(qb.buildSongSaveURL());

            StringEntity params =new StringEntity(qb.createSong(song));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            if(response.getStatusLine().getStatusCode()<205)
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (Exception e) {
            //e.getCause();
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        delegate.processFinish(aBoolean);
    }
}
