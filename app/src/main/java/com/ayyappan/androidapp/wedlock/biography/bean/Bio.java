package com.ayyappan.androidapp.wedlock.biography.bean;

import android.graphics.Bitmap;

/**
 * Created by Ayyappan on 05/12/2015.
 */
public class Bio {

    private String name;
    private String pictureUrl;
    private String bio;

    public Bio(String name, String picture, String bio) {
        this.name = name;
        this.pictureUrl = picture;
        this.bio = bio;
    }
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
