package com.ayyappan.androidapp.wedlock.venue.json;

/**
 * Created by Ayyappan on 31/10/2015.
 */

import com.ayyappan.androidapp.wedlock.venue.bean.Venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    public static final String VENUES = "Venues";


    public static final String ENGAGEMENT = "Engagement";
    public static final String RECEPTION = "Reception";
    public static final String WEDDING = "Wedding";

    public static final String NAME = "venueTitle";
    public static final String HALLNAME = "venueHall";
    public static final String ADDRESSLINE1 = "venueAddressLine1";
    public static final String ADDRESSLINE2 = "venueAddressLine2";
    public static final String ADDRESSLINE3 = "venueAddressLine3";
    public static final String ADDRESSLINE4 = "venueAddressLine4";
    public static final String COORDINATES = "venueCoordinates";

    public static List<Venue> getVenues(JSONObject jsonObject)
            throws JSONException {
        List<Venue> venues = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray(VENUES);
        Venue venue;
        for (int i = 0; i < jsonArray.length(); i++) {
            venue = new Venue();
            JSONObject venueObj = jsonArray.getJSONObject(i);
            venue.setVenueName(venueObj.getString(NAME));
            venue.setHallName(venueObj.getString(HALLNAME));
            venue.setAddressLine1(venueObj.getString(ADDRESSLINE1));
            venue.setAddressLine2(venueObj.getString(ADDRESSLINE2));
            venue.setAddressLine3(venueObj.getString(ADDRESSLINE3));
            venue.setCoordinateLat(venueObj.getDouble(COORDINATES));
            venue.setCoordinateLong(venueObj.getDouble(COORDINATES));
            venues.add(venue);
        }

        return venues;
    }
}