package com.ayyappan.androidapp.wedlock.utils;

/**
 * Created by Ayyappan on 31/10/2015.
 */


import android.content.Context;

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

public class VenueDetailsJsonReader {
    public static final String VENUES = "venues";

    public static final String EVENT_NAME = "eventTitle";
    public static final String EVENT_DATE = "eventDate";
    public static final String VENUE_NAME = "venueName";
    public static final String HALL_NAME = "hallName";

    public static final String ADDRESSLINE1 = "addressLine1";
    public static final String ADDRESSLINE2 = "addressLine2";
    public static final String ADDRESSLINE3 = "addressLine3";
    public static final String COORDINATES_LAT = "coordinateLat";
    public static final String COORDINATES_LONG = "coordinateLong";


    public static List<Venue> getVenues(Context context)
            throws JSONException {
        //Read json from file
        JSONObject jsonObject = new JSONObject(loadJSONFromAsset("VenueDetails.json", context));

        //Declare return venues list
        List<Venue> venues = null;

        //retrieve venues json array from json file
        JSONArray jsonArray = jsonObject.getJSONArray(VENUES);

        //Initialise return venues list if json has venue details
        if(jsonArray.length() > 0)
            venues = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            Venue venue = new Venue();
            JSONObject venueObj = jsonArray.getJSONObject(i);
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/M/YYYY HH:mm:ss");
            DateTime eventDate = formatter.parseDateTime(venueObj.getString(EVENT_DATE));

            venue.setEventName(venueObj.getString(EVENT_NAME));
            venue.setEventDate(eventDate);
            venue.setVenueName(venueObj.getString(VENUE_NAME));
            venue.setHallName(venueObj.getString(HALL_NAME));
            venue.setAddressLine1(venueObj.getString(ADDRESSLINE1));
            venue.setAddressLine2(venueObj.getString(ADDRESSLINE2));
            venue.setAddressLine3(venueObj.getString(ADDRESSLINE3));
            venue.setCoordinateLat(Double.parseDouble(venueObj.getString(COORDINATES_LAT)));
            venue.setCoordinateLong(Double.parseDouble(venueObj.getString(COORDINATES_LONG)));
            venues.add(venue);
        }

        return venues;
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
}