package com.ayyappan.androidapp.wedlock.database;

/**
 * Created by Ayyappan on 01/11/2015.
 */

import android.util.Log;

import com.google.gdata.client.authn.oauth.*;
import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.*;
import com.google.gdata.data.batch.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

public class MySpreadsheetIntegration {
    public static void main(String[] args)
            throws AuthenticationException, MalformedURLException, IOException, ServiceException {

        // Application code here
////////////////////////////////////////////////////////////////////////////
        // STEP 1: Configure how to perform OAuth 2.0
        ////////////////////////////////////////////////////////////////////////////

        // TODO: Update the following information with that obtained from
        // https://code.google.com/apis/console. After registering
        // your application, these will be provided for you.

        String CLIENT_ID = "643047232143-5l0vunu546e6ref7nkvan6vcfsqkinsb.apps.googleusercontent.com";

        // This is the OAuth 2.0 Client Secret retrieved
        // above.  Be sure to store this value securely.  Leaking this
        // value would enable others to act on behalf of your application!
        String CLIENT_SECRET = "Gc0230jdsah01jqpowpgff";

        // Space separated list of scopes for which to request access.
        String SCOPE = "https://spreadsheets.google.com/feeds https://docs.google.com/feeds";

        // This is the Redirect URI for installed applications.
        // If you are building a web application, you have to set your
        // Redirect URI at https://code.google.com/apis/console.
        String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

        ////////////////////////////////////////////////////////////////////////////
        // STEP 2: Set up the OAuth 2.0 object
      /*  ////////////////////////////////////////////////////////////////////////////

        // OAuth2Parameters holds all the parameters related to OAuth 2.0.
        OAuth2Parameters parameters = new OAuth2Parameters();

        // Set your OAuth 2.0 Client Id (which you can register at
        // https://code.google.com/apis/console).
        parameters.ClientId = CLIENT_ID;

        // Set your OAuth 2.0 Client Secret, which can be obtained at
        // https://code.google.com/apis/console.
        parameters.ClientSecret = CLIENT_SECRET;

        // Set your Redirect URI, which can be registered at
        // https://code.google.com/apis/console.
        parameters.RedirectUri = REDIRECT_URI;

        ////////////////////////////////////////////////////////////////////////////
        // STEP 3: Get the Authorization URL
        ////////////////////////////////////////////////////////////////////////////

        // Set the scope for this particular service.
        parameters.Scope = SCOPE;

        // Get the authorization url.  The user of your application must visit
        // this url in order to authorize with Google.  If you are building a
        // browser-based application, you can redirect the user to the authorization
        // url.
        String authorizationUrl = OAuthUtil.CreateOAuth2AuthorizationUrl(parameters);
        Log.d("urlstring authorizationUrl parser", authorizationUrl+"");

        Log.d("Please visit the URL above to authorize your OAuth "
                + "request token.  Once that is complete, type in your access code to "
                + "continue...","");
        parameters.AccessCode = Console.ReadLine();

        ////////////////////////////////////////////////////////////////////////////
        // STEP 4: Get the Access Token
        ////////////////////////////////////////////////////////////////////////////

        // Once the user authorizes with Google, the request token can be exchanged
        // for a long-lived access token.  If you are building a browser-based
        // application, you should parse the incoming request token from the url and
        // set it in OAuthParameters before calling GetAccessToken().
        OAuthUtil.GetAccessToken(parameters);
        string accessToken = parameters.AccessToken;
        Console.WriteLine("OAuth Access Token: " + accessToken);

        ////////////////////////////////////////////////////////////////////////////
        // STEP 5: Make an OAuth authorized request to Google
        ////////////////////////////////////////////////////////////////////////////

        // Initialize the variables needed to make the request
        GOAuth2RequestFactory requestFactory =
                new GOAuth2RequestFactory(null, "MySpreadsheetIntegration-v1", parameters);
        SpreadsheetsService service = new SpreadsheetsService("MySpreadsheetIntegration-v1");
        service.RequestFactory = requestFactory;
*/
        // Make the request to Google
        // See other portions of this guide for code to put here...
    SpreadsheetService service = new SpreadsheetService("MySpreadsheetIntegration");

    // TODO: Authorize the service object for a specific user (see other sections)

        // Define the URL to request.  This should never change.
        URL SPREADSHEET_FEED_URL = new URL(
                "https://spreadsheets.google.com/feeds/spreadsheets/17I2RPW-EO6NXsneJTX9cd_k9cqejhFGHEwKAes8UqV8");
        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
                SpreadsheetFeed.class);
/*
    // TODO: Choose a spreadsheet more intelligently based on your
    // app's needs.
    SpreadsheetEntry spreadsheet = spreadsheets.get(0);
    System.out.println(spreadsheet.getTitle().getPlainText());
*/

        // Get the first worksheet of the first spreadsheet.
        // TODO: Choose a worksheet more intelligently based on your
        // app's needs.
        WorksheetFeed worksheetFeed = service.getFeed(
                SPREADSHEET_FEED_URL, WorksheetFeed.class);
        List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
        WorksheetEntry worksheet = worksheets.get(0);

        // Fetch the list feed of the worksheet.
        URL listFeedUrl = worksheet.getListFeedUrl();
        ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

        // Iterate through each row, printing its cell values.
        for (ListEntry row : listFeed.getEntries()) {
            // Print the first column's cell value
            System.out.print(row.getTitle().getPlainText() + "\t");
            // Iterate over the remaining columns, and print each cell value
            for (String tag : row.getCustomElements().getTags()) {
                System.out.print(row.getCustomElements().getValue(tag) + "\t");
            }
            System.out.println();
        }
}

}
