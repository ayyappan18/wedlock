package com.ayyappan.androidapp.wedlock.database.googledrive;

import android.os.AsyncTask;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ayyappan on 01/12/2015.
 */
public class GoogleSpreadsheetDataSource {

    private static final String SPREADSHEET_SERVICE_URL
            = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";

    private static SpreadsheetService service;

    public GoogleSpreadsheetDataSource(){
        service = new SpreadsheetService("spreadsheetservice");
        try {
            service.setUserCredentials("ayyappan18@gmail.com", "nithyanirmal1");
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }
    }

    public String[] getImageURLs() throws IOException, ServiceException, URISyntaxException {
    //    WorksheetEntry worksheet = getWorkSheet("Wedlock", "Images");
        WorksheetEntry worksheet = getFeed("17I2RPW-EO6NXsneJTX9cd_k9cqejhFGHEwKAes8UqV8");

        System.out.println("*********worksheet" + worksheet.getTitle().getPlainText());

        URL listFeedUrl = worksheet.getListFeedUrl();

        ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

        List<String> imageUrls = new ArrayList<String>();
        /*for (ListEntry row : listFeed.getEntries()) {
            Map<String,Object> rowValues = getRowData(row);
            imageUrls.add(rowValues.get("ImageUrls").toString());
        }*/
        System.out.println("*********size" + listFeed.getTotalResults());
        System.out.println("*********title" + listFeed.getTitle().getPlainText());
        System.out.println("*********set" + listFeed.getCategories().size());
        System.out.println("*********feedurl" + listFeedUrl.toURI().toString());

        //   System.out.println("*********subtitle" + listFeed.getSubtitle().getPlainText());

        // Iterate through each row, printing its cell values.
        for (ListEntry row : listFeed.getEntries()) {
            Integer b = row.getCustomElements().getTags().size();

            String a = row.getCustomElements().getValue("ImageUrls");
            imageUrls.add(row.getTitle().getPlainText());
        }

     /*   // Fetch column 4, and every row after row 1.
        URL cellFeedUrl = new URI(worksheet.getCellFeedUrl().toString()
                + "?min-row=2&min-col=1&max-col=1").toURL();
        CellFeed cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);
        // Print the cell's displayed value (useful if the cell has a formula)
        System.out.println("*****"+cellFeed.getTotalResults());
        System.out.println("*****"+cellFeedUrl.toURI().toString());
        System.out.println("*****"+cellFeed.getColCount());
        System.out.println("*****"+cellFeed.getRowCount());

        // Iterate through each cell, printing its value.
        for (CellEntry cell : cellFeed.getEntries()) {
            // Print the cell's formula or text value
            System.out.print("*****"+cell.getCell().getInputValue() + "\t");
            // Print the cell's displayed value (useful if the cell has a formula)
            System.out.println("*****" + cell.getCell().getValue() + "\t");
            // Print the cell's address in A1 notation
            System.out.print("*****"+cell.getTitle().getPlainText() + "\t");
            // Print the cell's address in R1C1 notation
            System.out.print("*****"+cell.getId().substring(cell.getId().lastIndexOf('/') + 1) + "\t");

            // Print the cell's calculated value if the cell's value is numeric
            // Prints empty string if cell's value is not numeric
            System.out.print("*****" + cell.getCell().getNumericValue() + "\t");

        }
*/

        String []result = new String[imageUrls.size()];
        return imageUrls.toArray(result);
    }

    private WorksheetEntry getFeed(String key) throws IOException, ServiceException {
        //SPREADSHEET URL
        URL SPREADSHEET_FEED_URL = FeedURLFactory.getDefault().getWorksheetFeedUrl(key, "public", "basic");
        WorksheetFeed worksheetFeed = service.getFeed(
                SPREADSHEET_FEED_URL, WorksheetFeed.class);


        List<WorksheetEntry> worksheetList = worksheetFeed.getEntries();
        return worksheetList.get(1);
    }



    private WorksheetEntry getWorkSheet(String sheetName, String workSheetName) {
        try {
            SpreadsheetEntry spreadsheet = getSpreadsheet(sheetName);

            if (spreadsheet != null) {
                WorksheetFeed worksheetFeed = service.getFeed(
                        spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
                List<WorksheetEntry> worksheets = worksheetFeed.getEntries();

                for (WorksheetEntry worksheetEntry : worksheets) {
                    String wktName = worksheetEntry.getTitle().getPlainText();
                    if (wktName.equals(workSheetName)) {
                        return worksheetEntry;
                    }
                }
            }
            else{
                System.out.println("");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private SpreadsheetEntry getSpreadsheet(String sheetName) {
        try {
            URL spreadSheetFeedUrl = new URL(SPREADSHEET_SERVICE_URL);

            SpreadsheetQuery spreadsheetQuery = new SpreadsheetQuery(
                    spreadSheetFeedUrl);
            spreadsheetQuery.setTitleQuery(sheetName);
            spreadsheetQuery.setTitleExact(true);
            SpreadsheetFeed spreadsheet = service.getFeed(spreadsheetQuery,
                    SpreadsheetFeed.class);

            if (spreadsheet.getEntries() != null
                    && spreadsheet.getEntries().size() == 1) {
                return spreadsheet.getEntries().get(0);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private Map<String, Object> getRowData(ListEntry row) {
        Map<String, Object> rowValues = new HashMap<String, Object>();
        for (String tag : row.getCustomElements().getTags()) {
            System.out.println("*******" + row.getCustomElements().getTags());
            Object value = row.getCustomElements().getValue(tag);
            rowValues.put(tag, value);
        }
        return rowValues;
    }

}
