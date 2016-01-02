package com.ayyappan.androidapp.wedlock.model;

/**
 * Created by Ayyappan on 05/12/2015.
 */
public class Bio {

    private String name;
    private int pictureResourceId;
    private String bio;
    private String pictureUrl;

    public Bio(){ }

    public Bio(String name, int pictureResourceId, String bio) {
        this.name = name;
        this.pictureResourceId = pictureResourceId;
        this.bio = bio;
    }

    public Bio(String name, String pictureUrl, String bio) {
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.bio = bio;
    }

    public Bio(String name, String pictureUrl, int pictureResourceId, String bio) {
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.bio = bio;
        this.pictureResourceId = pictureResourceId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getPictureResourceId() {
        return pictureResourceId;
    }

    public void setPictureResourceId(int pictureUrl) {
        this.pictureResourceId = pictureUrl;
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
