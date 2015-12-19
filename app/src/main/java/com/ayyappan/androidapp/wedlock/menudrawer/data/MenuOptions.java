package com.ayyappan.androidapp.wedlock.menudrawer.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ayyappan on 03/11/2015.
 */
public class MenuOptions {

    private static List<String> menuGroupHeaders;
    private static HashMap<String, List<String>> menuGroupCompleteList;

    public static final String HOME = "Home";
    public static final String BIOGRAPHY = "Couple Profile";
    public static final String BIOGRAPHY_BRIDE = "Bride";
    public static final String BIOGRAPHY_GROOM = "Groom";
    public static final String GALLERY = "Gallery";
    public static final String ENTERTAINMENT = "Entertainment";
    public static final String ENTERTAINMENT_LIGHTMUSIC = "Light music";
    public static final String ENTERTAINMENT_SANGEETH = "Sangeeth";
    public static final String BLOG = "Blog";
    public static final String INVITATION = "Invitation";
    public static final String VENUE = "Venue";
    public static final String VENUE_ENGAGEMENT = "Engagement";
    public static final String VENUE_RECEPTION = "Reception";
    public static final String VENUE_WEDDING = "Wedding";
    public static final String ABOUT = "About";


    private static MenuOptions ourInstance = new MenuOptions();

    public static MenuOptions getInstance() {
        return ourInstance;
    }

    private MenuOptions() {
        prepareData();
    }

    public static List<String> getMenuGroupHeaders() {
        return menuGroupHeaders;
    }

    public static HashMap<String, List<String>> getMenuGroupCompleteList() {
        return menuGroupCompleteList;
    }

    private static void prepareData(){
        menuGroupHeaders = new ArrayList<String>();
        menuGroupCompleteList = new HashMap<String, List<String>>();

        // Adding child data
        menuGroupHeaders.add(HOME);
        menuGroupHeaders.add(BIOGRAPHY);
        menuGroupHeaders.add(GALLERY);
        menuGroupHeaders.add(ENTERTAINMENT);
        menuGroupHeaders.add(BLOG);
        menuGroupHeaders.add(INVITATION);
        menuGroupHeaders.add(VENUE);
        menuGroupHeaders.add(ABOUT);

        // Adding child data
        List<String> entertainment = new ArrayList<String>();
        entertainment.add(ENTERTAINMENT_LIGHTMUSIC);
        entertainment.add(ENTERTAINMENT_SANGEETH);

        List<String> biography = new ArrayList<String>();
        biography.add(BIOGRAPHY_BRIDE);
        biography.add(BIOGRAPHY_GROOM);

        List<String> venue = new ArrayList<String>();
        venue.add(VENUE_ENGAGEMENT);
        venue.add(VENUE_RECEPTION);
        venue.add(VENUE_WEDDING);

        menuGroupCompleteList.put(menuGroupHeaders.get(0), new ArrayList()); // Header, Child data
        menuGroupCompleteList.put(menuGroupHeaders.get(1), biography);
        menuGroupCompleteList.put(menuGroupHeaders.get(2), new ArrayList());
        menuGroupCompleteList.put(menuGroupHeaders.get(3), entertainment);
        menuGroupCompleteList.put(menuGroupHeaders.get(4), new ArrayList());
        menuGroupCompleteList.put(menuGroupHeaders.get(5), new ArrayList());
        menuGroupCompleteList.put(menuGroupHeaders.get(6), venue);
        menuGroupCompleteList.put(menuGroupHeaders.get(7), new ArrayList());

    }
}
