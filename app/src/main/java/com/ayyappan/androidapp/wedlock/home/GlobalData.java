package com.ayyappan.androidapp.wedlock.home;

import android.content.Context;

import com.ayyappan.androidapp.wedlock.biography.bean.Couple;
import com.ayyappan.androidapp.wedlock.database.local.DBHelper;
import com.ayyappan.androidapp.wedlock.login.bean.User;
import com.ayyappan.androidapp.wedlock.venue.VenueActivity;
import com.ayyappan.androidapp.wedlock.venue.bean.Venue;
import com.ayyappan.androidapp.wedlock.venue.json.VenueDetailsJsonReader;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayyappan on 05/12/2015.
 */
public class GlobalData {

    private static Couple couple = null;
    private static List<Venue> venues = null;
    private static String[] IMAGES_URLS = null;
    private static String userName = null;
    private static User user = null;
    private Context context = null;
    private DBHelper db;
    private static GlobalData obj;

    public GlobalData(Context context) {
        this.context = context;
        db = new DBHelper(context);
    }

    public List<Venue> getVenue() {
        if(GlobalData.venues == null){
            try {
                List<Venue> venues = VenueDetailsJsonReader.getVenues(context);
                GlobalData.venues = venues;
                return venues;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }else
            return GlobalData.venues;
    }

    public void setVenue(List<Venue> venue) {
        GlobalData.venues = venue;
    }

    public Couple getCouple() {
      //  if(GlobalData.couple == null)
            return db.getCouple();
       // else return couple;
    }

    public void setCouple(Couple couple) {
        if(db.insertCouple(couple))
            GlobalData.couple = couple;
    }

    public String[] getImagesUrls() {
        if(GlobalData.IMAGES_URLS == null) {
            ArrayList<String> imageUrls = db.getAllImageUrls();
            if (imageUrls.size() > 0) {
                String[] result = new String[imageUrls.size()];
                GlobalData.IMAGES_URLS = result;
                return imageUrls.toArray(result);
            } else
                return null;
        }
        else
            return GlobalData.IMAGES_URLS;
    }

    public void setImagesUrls(String[] imagesUrls) {
        if(db.insertImageUrls(imagesUrls)) GlobalData.IMAGES_URLS = imagesUrls;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        GlobalData.userName = userName;
    }

    public User getUser() {
        if(GlobalData.user == null) {
            User retrievedUser = db.retrieveUser();
            if(retrievedUser != null)
                GlobalData.user = retrievedUser;
            return retrievedUser;
        }
        else
            return GlobalData.user;
    }

    public void setUser(User user) {
        if(db.saveUser(user)) GlobalData.user = user;
    }
}
