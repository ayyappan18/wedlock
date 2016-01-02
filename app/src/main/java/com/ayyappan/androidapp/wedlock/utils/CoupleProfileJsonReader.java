package com.ayyappan.androidapp.wedlock.utils;

/**
 * Created by Ayyappan on 31/10/2015.
 */


import android.content.Context;

import com.ayyappan.androidapp.wedlock.model.Bio;
import com.ayyappan.androidapp.wedlock.model.Couple;
import com.ayyappan.androidapp.wedlock.model.Venue;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CoupleProfileJsonReader {

    public static final String BRIDE = "bride";
    public static final String GROOM = "groom";

    public static final String NAME = "name";
    public static final String PICTURE = "picture";
    public static final String BIO = "bio";


    public Couple getCouple(Context context)
            throws JSONException {

        JSONObject jsonObject = new JSONObject(loadJSONFromAsset("CoupleProfile.json", context));

        Couple couple = new Couple();

        JSONObject brideJson = jsonObject.getJSONObject(BRIDE);
        Bio bride = new Bio();
        bride.setName(brideJson.getString(NAME));
        bride.setPictureResourceId(getResourceId(brideJson.getString(PICTURE), context));
        bride.setBio(brideJson.getString(BIO));
        couple.setBride(bride);

        JSONObject groomJson = jsonObject.getJSONObject(GROOM);
        Bio groom = new Bio();
        groom.setName(groomJson.getString(NAME));
        groom.setPictureResourceId(getResourceId(groomJson.getString(PICTURE), context));
        groom.setBio(groomJson.getString(BIO));
        couple.setGroom(groom);

        return couple;
    }

    private static String loadJSONFromAsset(String jsonFilePath, Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonFilePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static int getResourceId(String name, Context context) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());

    }
}