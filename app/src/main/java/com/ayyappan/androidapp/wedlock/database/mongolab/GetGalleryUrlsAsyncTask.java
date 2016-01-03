package com.ayyappan.androidapp.wedlock.database.mongolab;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.home.GlobalData;
import com.ayyappan.androidapp.wedlock.model.AppData;
import com.ayyappan.androidapp.wedlock.model.Bio;
import com.ayyappan.androidapp.wedlock.model.Couple;
import com.ayyappan.androidapp.wedlock.model.Image;
import com.ayyappan.androidapp.wedlock.utils.CoupleProfileJsonReader;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayyappan on 03/01/2016.
 */
public class GetGalleryUrlsAsyncTask extends AsyncTask<Void, Void, List<Image>> {
    private static String server_output = null;
    private static String temp_output = null;
    private Context context;

    public interface AsyncResponse {
        void processFinish(String[] imageUrls);
    }

    public AsyncResponse delegate = null;

    public GetGalleryUrlsAsyncTask(Context context,AsyncResponse delegate){
        this.context = context;
        this.delegate = delegate;
    }
    @Override
    protected List<Image> doInBackground(Void... arg0) {
        List<Image> images = new ArrayList<>();
        try {
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
            Object o = com.mongodb.util.JSON.parse(server_output);


            DBObject doc = (DBObject) ((BasicDBList)o).get(0);

            BasicDBList imageUrlsObj = (BasicDBList) doc.get("images");

            for (Object obj : imageUrlsObj) {
                DBObject imageObj = (DBObject) obj;

                Image temp = new Image();
                temp.setImageId((Integer) imageObj.get("imageId"));
                temp.setFullsizeUri((String) imageObj.get("fullsizeUri"));
                temp.setThumbnailUri((String) imageObj.get("fullsizeUri"));
                images.add(temp);
            }
            return images;

        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Image> images) {
        String[] imageUrls = new String[images.size()];
        int count = 0;
        for(Image item:images){
            imageUrls[count] = item.getFullsizeUri();
            count++;
        }
        delegate.processFinish(imageUrls);
        /*if(images!=null){
            Toast.makeText(context, "Gallery details are downloaded.", Toast.LENGTH_SHORT).show();
            String[] imageUrls = new String[images.size()];
            int count = 0;
            for(Image item:images){
                imageUrls[count] = item.getFullsizeUri();
                count++;
            }
            new GlobalData(context).setImagesUrls(imageUrls);
        }*/
    }
}
