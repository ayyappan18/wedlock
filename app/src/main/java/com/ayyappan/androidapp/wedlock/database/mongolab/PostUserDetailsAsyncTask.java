package com.ayyappan.androidapp.wedlock.database.mongolab;

import android.content.Context;
import android.os.AsyncTask;

import com.ayyappan.androidapp.wedlock.model.Bio;
import com.ayyappan.androidapp.wedlock.model.Couple;
import com.ayyappan.androidapp.wedlock.model.User;
import com.ayyappan.androidapp.wedlock.utils.CoupleProfileJsonReader;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ayyappan on 03/01/2016.
 */
public class PostUserDetailsAsyncTask extends AsyncTask<User, Void, Boolean> {

    public PostUserDetailsAsyncTask(){ }

    @Override
    protected Boolean doInBackground(User... arg0) {
        User user = arg0[0];
        try {

            QueryBuilder qb = new QueryBuilder();

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(qb.buildUserSaveURL());

            StringEntity params =new StringEntity(qb.createUser(user));
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
}
