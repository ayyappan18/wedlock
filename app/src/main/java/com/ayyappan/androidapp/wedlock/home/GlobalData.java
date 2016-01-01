package com.ayyappan.androidapp.wedlock.home;

import android.content.Context;

import com.ayyappan.androidapp.wedlock.biography.bean.Couple;
import com.ayyappan.androidapp.wedlock.database.local.DBHelper;
import com.ayyappan.androidapp.wedlock.login.bean.User;
import com.ayyappan.androidapp.wedlock.venue.bean.Venue;
import com.ayyappan.androidapp.wedlock.venue.json.VenueDetailsJsonReader;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayyappan on 05/12/2015.
 */
public class GlobalData {

    private Context context = null;
    private DBHelper db;

    public GlobalData(Context context) {
        this.context = context;
        db = new DBHelper(context);
    }

    public List<Venue> getVenue() {
        try {
            List<Venue> venues = VenueDetailsJsonReader.getVenues(context);
            return venues;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Couple getCouple() {
        return db.getCouple();
    }

    public void setCouple(Couple couple) {
        db.insertCouple(couple);
    }

    public String[] getImagesUrls() {
        List<String> imageUrls = db.getAllImageUrls();
        if (imageUrls.size() > 0) {
            String[] result = new String[imageUrls.size()];
            return imageUrls.toArray(result);
        } else
            return null;
    }

    public void setImagesUrls(String[] imagesUrls) {
        db.insertImageUrls(imagesUrls);
    }

    public User getUser() {
        return db.retrieveUser();
    }

    public void setUser(User user) {
        db.saveUser(user);
    }
}
