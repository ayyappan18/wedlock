package com.ayyappan.androidapp.wedlock.database.mongolab;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.model.Bio;
import com.ayyappan.androidapp.wedlock.model.Couple;
import com.ayyappan.androidapp.wedlock.utils.CoupleProfileJsonReader;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ayyappan on 03/01/2016.
 */
public class GetCoupleProfileAsyncTask  extends AsyncTask<Void, Void, Couple> {
    private static String server_output = null;
    private static String temp_output = null;
    private Context context;
    public interface AsyncResponse {
        void processFinish(Couple couple);
    }

    public AsyncResponse delegate = null;

    public GetCoupleProfileAsyncTask(Context context,AsyncResponse delegate){
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    protected Couple doInBackground(Void... arg0) {
        try
        {
            QueryBuilder qb = new QueryBuilder();
            URL url = new URL(qb.buildAppDetailsGetURL());
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            while ((temp_output = br.readLine()) != null) {
                server_output = temp_output;
            }

            // create a basic db list
            DBObject o = (DBObject) com.mongodb.util.JSON.parse(server_output);

            DBObject doc = (DBObject) ((BasicDBList)o).get(0);

            DBObject imageUrlsObj = (DBObject) doc.get("couple");

            Couple couple = new Couple();

            DBObject brideDbObject = (DBObject)imageUrlsObj.get("bride");
            Bio bride = new Bio((String)brideDbObject.get("name"),(String)brideDbObject.get("picture"), CoupleProfileJsonReader.getResourceId((String) brideDbObject.get("pictureOffline"), context),(String)brideDbObject.get("bio"));
            couple.setBride(bride);

            DBObject groomDbObject = (DBObject)imageUrlsObj.get("groom");
            Bio groom = new Bio((String)groomDbObject.get("name"),(String)groomDbObject.get("picture"),CoupleProfileJsonReader.getResourceId((String)groomDbObject.get("pictureOffline"),context), (String)groomDbObject.get("bio"));

            couple.setGroom(groom);
            return couple;

        }catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Couple couple) {
            delegate.processFinish(couple);
           /* Toast.makeText(context, "Couple profile details are downloaded.", Toast.LENGTH_SHORT).show();
            new GlobalData(context).setCouple(couple);*/
    }
}
