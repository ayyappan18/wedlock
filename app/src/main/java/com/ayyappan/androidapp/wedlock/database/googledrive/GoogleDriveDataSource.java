package com.ayyappan.androidapp.wedlock.database.googledrive;

import android.util.Log;

import com.ayyappan.androidapp.wedlock.database.DataSource;
import com.ayyappan.androidapp.wedlock.venue.bean.Venue;

import com.google.gdata.client.Service;
import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import org.joda.time.DateTime;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ayyappan on 01/11/2015.
 */
public class GoogleDriveDataSource implements DataSource {

    public static String EVENT_NAME = "eventname";
    public static String VENUE_NAME = "venuename";
    public static String HALL_NAME = "hallname";
    public static String ADDRESSLINE1 = "venueaddressline1";

    @Override
    public List<Venue> getVenues(String key) throws IOException, ServiceException {

        List<Venue> venues;

        //SPREADSHEET URL
        URL SPREADSHEET_FEED_URL = FeedURLFactory.getDefault().getWorksheetFeedUrl(key, "public", "basic");

        //GET THE SPREADSHEET
        SpreadsheetService service = new SpreadsheetService("Wedlock");

      /*  SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();

        if (spreadsheets.size() == 0) {
            // TODO: There were no spreadsheets, act accordingly.
        }

        // TODO: Choose a spreadsheet more intelligently based on your
        // app's needs.
        SpreadsheetEntry spreadsheet = spreadsheets.get(0);
        try {
            System.out.println("***" + "Spreadsheet Feed Url:" + spreadsheet.getWorksheetFeedUrl().toURI().toString());
            System.out.println("***" + "Spreadsheet Title:" + spreadsheet.getTitle().getPlainText());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }*/

        WorksheetFeed worksheetFeed = service.getFeed(
                SPREADSHEET_FEED_URL, WorksheetFeed.class);


        List<WorksheetEntry> worksheetList = worksheetFeed.getEntries();
        WorksheetEntry worksheetEntry = worksheetList.get(0);
            System.out.println("***" + "Worksheet Row count:" + worksheetEntry.getRowCount());
            System.out.println("***" + "Worksheet Title:" + worksheetEntry.getTitle().getPlainText());


        // Get the first worksheet of the first spreadsheet.
        // TODO: Choose a worksheet more intelligently based on your
        // app's needs.
/*
        WorksheetFeed worksheetFeed = service.getFeed(
                spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
        List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
        WorksheetEntry worksheet = worksheets.get(0);
*/

        // Fetch the list feed of the worksheet.
        URL listFeedUrl = worksheetEntry.getListFeedUrl();
        ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
        System.out.print("*********size" +listFeed.getTotalResults());
        System.out.print("*********size" +listFeed);

        venues = new ArrayList<>();
        Venue venue = new Venue();
        // Iterate through each row, printing its cell values.
        for (ListEntry row : listFeed.getEntries()) {
            getRowData(row);
            venues.add(venue);
        }
        //if(venues.size()==0){
            List<Venue> v = new ArrayList<>();
        v.add(new Venue("Engagement", new DateTime("2016-02-10"),"Hotel Savera","Samvesh","146, Dr.Radhakrishnan Road","Mylapore, Chennai","Tamil Nadu 600004, India",13.045239, 80.261680));
        v.add(new Venue("Reception", new DateTime("2016-02-10"),"Hotel Savera","Samvesh","146, Dr.Radhakrishnan Road","Mylapore, Chennai","Tamil Nadu 600004, India",13.045323, 80.071514));
        v.add(new Venue("Wedding", new DateTime("2016-02-10"),"Hotel Savera","Samvesh","146, Dr.Radhakrishnan Road","Mylapore, Chennai","Tamil Nadu 600004, India",13.045323, 80.071514));
        return v;
       // }else
       //     return venues;
    }

    @Override
    public String[] getImagesURLs(String key) throws IOException, ServiceException {
        ArrayList<String> imageURLs;

        //SPREADSHEET URL
        URL SPREADSHEET_FEED_URL = FeedURLFactory.getDefault().getWorksheetFeedUrl(key, "public", "basic");

        //GET THE SPREADSHEET
        SpreadsheetService service = new SpreadsheetService("Wedlock");

      /*  SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();

        if (spreadsheets.size() == 0) {
            // TODO: There were no spreadsheets, act accordingly.
        }

        // TODO: Choose a spreadsheet more intelligently based on your
        // app's needs.
        SpreadsheetEntry spreadsheet = spreadsheets.get(0);
        try {
            System.out.println("***" + "Spreadsheet Feed Url:" + spreadsheet.getWorksheetFeedUrl().toURI().toString());
            System.out.println("***" + "Spreadsheet Title:" + spreadsheet.getTitle().getPlainText());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }*/

        WorksheetFeed worksheetFeed = service.getFeed(
                SPREADSHEET_FEED_URL, WorksheetFeed.class);


        List<WorksheetEntry> worksheetList = worksheetFeed.getEntries();
        WorksheetEntry worksheetEntry = worksheetList.get(0);
        System.out.println("***" + "Worksheet Row count:" + worksheetEntry.getRowCount());
        System.out.println("***" + "Worksheet Title:" + worksheetEntry.getTitle().getPlainText());

        // Fetch the list feed of the worksheet.
        URL listFeedUrl = worksheetEntry.getListFeedUrl();
        ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
        System.out.print("*********size" + listFeed.getTotalResults());
        System.out.print("*********size" + listFeed);

        imageURLs = new ArrayList<>();
        // Iterate through each row, printing its cell values.
        for (ListEntry row : listFeed.getEntries()) {
            imageURLs.add(row.getCustomElements().getValue("Image_URL"));
        }

        String []result = new String[imageURLs.size()];
        return imageURLs.toArray(result);

    }

    private Venue getRowData(ListEntry row) {
        Venue venue = new Venue();
        System.out.println("***Tags:" +  row.getCustomElements().getTags());
        System.out.println("***ADDRESSLINE1:" + row.getCustomElements().getValue(VENUE_NAME));
        System.out.println("***ADDRESSLINE1:" + row.getCustomElements().getValue("eventname"));

        venue.setVenueName(row.getCustomElements().getValue(VENUE_NAME));
        venue.setAddressLine1(row.getCustomElements().getValue(ADDRESSLINE1));

        return venue;
    }

}
