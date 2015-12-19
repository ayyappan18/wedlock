package com.ayyappan.androidapp.wedlock.imageslideshow.json;

/**
 * Created by Ayyappan on 31/10/2015.
 */
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ayyappan.androidapp.wedlock.imageslideshow.bean.Image;
import com.ayyappan.androidapp.wedlock.imageslideshow.utils.TagName;

public class JsonReader {

    public static List<Image> getHome(JSONObject jsonObject)
            throws JSONException {
        List<Image> products = new ArrayList<Image>();

        JSONArray jsonArray = jsonObject.getJSONArray(TagName.TAG_PRODUCTS);
        Image product;
        for (int i = 0; i < jsonArray.length(); i++) {
            product = new Image();
            JSONObject productObj = jsonArray.getJSONObject(i);
            product.setId(productObj.getInt(TagName.KEY_ID));
            product.setName(productObj.getString(TagName.KEY_NAME));
            product.setImageUrl(productObj.getString(TagName.KEY_IMAGE_URL));

            products.add(product);
        }
        return products;
    }
}