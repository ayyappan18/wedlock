package com.ayyappan.androidapp.wedlock.gallery.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gdata.client.*;
import com.google.gdata.client.photos.*;
import com.google.gdata.data.*;
import com.google.gdata.data.photos.*;

/**
 * Created by Ayyappan on 05/12/2015.
 */
public class PicasaImageUrlExtractor {

    public static void main(String args[]) throws MalformedURLException {

        PicasawebService myService = new PicasawebService("Wedlock_Image_Extractor");
      //  myService.setUserCredentials("liz@gmail.com", "mypassword");

        URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/ayyappan18/albumid/109013171581144783810");

     //   AlbumFeed feed = myService.getFeed(feedUrl, AlbumFeed.class);

   /*     for(PhotoEntry photo : feed.getPhotoEntries()) {
            System.out.println(photo.getTitle().getPlainText());
        }*/
    }
}


//GET https://picasaweb.google.com/data/feed/api/user/userID/albumid/albumID