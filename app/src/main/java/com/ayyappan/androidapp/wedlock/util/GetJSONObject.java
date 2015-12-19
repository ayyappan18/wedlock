package com.ayyappan.androidapp.wedlock.util;

/**
 * Created by Ayyappan on 31/10/2015.
 */

import org.json.JSONObject;
import java.io.IOException;
import org.json.JSONException;
import android.os.Build;

import com.ayyappan.androidapp.wedlock.util.JSONParser;

public class GetJSONObject {

    public static JSONObject getJSONObject(String url) throws IOException,
            JSONException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        // Use HttpURLConnection
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            jsonObject = jsonParser.getJSONHttpURLConnection(url);
        } else {
            // use HttpClient
            jsonObject = jsonParser.getJSONHttpClient(url);
        }
        return jsonObject;
    }
}
