package com.ayyappan.androidapp.wedlock.database.mongolab;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.activities.ApplicationActivity;
import com.ayyappan.androidapp.wedlock.database.local.DBHelper;
import com.ayyappan.androidapp.wedlock.model.Rsvp;
import com.ayyappan.androidapp.wedlock.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.lang.ref.WeakReference;

/**
 * Created by Ayyappan on 03/01/2016.
 */
public class PostRSVPResponseAsyncTask extends AsyncTask<Rsvp, Void, Rsvp> {

    private Context context;

    public PostRSVPResponseAsyncTask(Context context){ this.context = context;}

    @Override
    protected Rsvp doInBackground(Rsvp... arg0) {
        Rsvp rsvp = arg0[0];
        try {

            QueryBuilder qb = new QueryBuilder();

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(qb.buildRsvpResponseURL());

            StringEntity params =new StringEntity(qb.createRsvp(rsvp));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse httpResponse = httpClient.execute(request);

            if(httpResponse.getStatusLine().getStatusCode()<205)
            {
                String response = EntityUtils.toString(httpResponse.getEntity());

                Gson gson = new Gson();
                JsonElement element = gson.fromJson (response, JsonElement.class);
                JsonObject jsonObj = element.getAsJsonObject();
                String oid = jsonObj.get("_id").getAsJsonObject().get("$oid").getAsString();
                rsvp.setOid(oid);
                return rsvp;
            }
            else
            {
                return null;
            }
        } catch (Exception e) {
            //e.getCause();
            System.out.println(e.getMessage());
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
