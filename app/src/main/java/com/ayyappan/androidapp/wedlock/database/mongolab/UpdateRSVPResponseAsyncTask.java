package com.ayyappan.androidapp.wedlock.database.mongolab;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.database.local.DBHelper;
import com.ayyappan.androidapp.wedlock.model.Rsvp;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ayyappan on 03/01/2016.
 */
public class UpdateRSVPResponseAsyncTask extends AsyncTask<Rsvp, Void, Rsvp> {

    private Context context;
    public UpdateRSVPResponseAsyncTask(Context context){ this.context = context; }

    @Override
    protected Rsvp doInBackground(Rsvp... arg0) {
        Rsvp rsvp = arg0[0];
        try{
        QueryBuilder qb = new QueryBuilder();
        URL url = new URL(qb.buildRsvpResponseUpdateURL(rsvp.getOid()));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type",
                "application/json");
        connection.setRequestProperty("Accept", "application/json");

        OutputStreamWriter osw = new OutputStreamWriter(
                connection.getOutputStream());

        osw.write(qb.updateRsvp(rsvp));
        osw.flush();
        osw.close();
        if(connection.getResponseCode() <205)
        {
            return rsvp;
        }
        else
        {
            return null;

        }

        } catch (Exception e) {
            e.getMessage();
            return null;

        }
    }

    @Override
    protected void onPostExecute(Rsvp rsvp) {
        if(rsvp!=null) {
            DBHelper local = new DBHelper(context);
            local.insertOrUpdateRsvp(rsvp);
            Toast.makeText(context, "Your response is sent", Toast.LENGTH_SHORT);
        }
    }
}
