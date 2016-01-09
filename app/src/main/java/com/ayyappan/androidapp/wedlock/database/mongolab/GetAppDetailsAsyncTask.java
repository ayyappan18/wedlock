package com.ayyappan.androidapp.wedlock.database.mongolab;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ayyappan.androidapp.wedlock.database.local.DBHelper;
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
import java.util.Objects;

/**
 * Created by Ayyappan on 03/01/2016.
 */
public class GetAppDetailsAsyncTask extends AsyncTask<Void, Void, AppData> {
    private static String server_output = null;
    private static String temp_output = null;
    private Context context;

    public interface AsyncResponse {
        void processFinish(AppData output);
    }

    public AsyncResponse delegate = null;

    public GetAppDetailsAsyncTask(Context context){
        this.context = context;
     //   this.delegate = delegate;
    }

    @Override
    protected AppData doInBackground(Void... arg0) {
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
            Object o = com.mongodb.util.JSON.parse(server_output);

            DBObject doc = (DBObject) ((BasicDBList)o).get(0);

            AppData data = new AppData();
            DBObject coupleDbObject = (DBObject)doc.get("couple");
            BasicDBList imagesDBList = (BasicDBList) doc.get("images");

            List<Image> images = getImages(imagesDBList);
            Couple couple = getCouple(coupleDbObject);

            data.setCouple(couple);
            data.setImages(images);

            return data;

        }catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private Couple getCouple(DBObject coupleDbObject){
        Couple couple = new Couple();
        DBObject brideDbObject = (DBObject)coupleDbObject.get("bride");
        Bio bride = new Bio((String)brideDbObject.get("name"),(String)brideDbObject.get("picture"), CoupleProfileJsonReader.getResourceId((String) brideDbObject.get("pictureOffline"), context),(String)brideDbObject.get("bio"));
        couple.setBride(bride);

        DBObject groomDbObject = (DBObject)coupleDbObject.get("groom");
        Bio groom = new Bio((String)groomDbObject.get("name"),(String)groomDbObject.get("picture"),CoupleProfileJsonReader.getResourceId((String)groomDbObject.get("pictureOffline"),context), (String)groomDbObject.get("bio"));

        couple.setGroom(groom);
        return couple;
    }

    private List<Image> getImages(BasicDBList imageUrlsObj){
        List<Image> images = new ArrayList<>();

        for (Object obj : imageUrlsObj) {
            DBObject imageObj = (DBObject) obj;
            Image temp = new Image();
            temp.setImageId((Integer) imageObj.get("imageId"));
            temp.setFullsizeUri((String) imageObj.get("fullsizeUri"));
            temp.setThumbnailUri((String) imageObj.get("fullsizeUri"));
            images.add(temp);
        }
        return images;
    }

/*    @Override
    protected void onPostExecute(AppData appData) {
        if(appData!=null){
            Toast.makeText(context, "App Details downloaded new Mongo", Toast.LENGTH_SHORT).show();
            DBHelper localDb = new DBHelper(context);
            localDb.insertCouple(appData.getCouple());
            String[] imageUrls = new String[appData.getImages().size()];
            int count = 0;
            for(Image item:appData.getImages()){
                imageUrls[count] = item.getFullsizeUri();
                count++;
            }
            localDb.insertImageUrls(imageUrls);
        }
    }*/

    @Override
    protected void onPostExecute(AppData appData) {
      //  delegate.processFinish(data);
        if(appData!=null){
      //      Toast.makeText(context, "App Details downloaded new Mongo", Toast.LENGTH_SHORT).show();
            DBHelper localDB = new DBHelper(context);
            localDB.insertCouple(appData.getCouple());
            String[] imageUrls = new String[appData.getImages().size()];
            int count = 0;
            for(Image item:appData.getImages()){
                imageUrls[count] = item.getFullsizeUri();
                count++;
            }
            localDB.insertImageUrls(imageUrls);
        }
    }
}
