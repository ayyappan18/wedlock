package com.ayyappan.androidapp.wedlock.model;

/**
 * Created by Ayyappan on 04/12/2015.
 */
public class Image {
    private Integer imageId;
    private String thumbnailUri;
    private String fullsizeUri;

    public Image(){ }
    public Image(Integer imageId, String thumbnailUri, String fullsizeUri) {
        this.imageId = imageId;
        this.thumbnailUri = thumbnailUri;
        this.fullsizeUri = fullsizeUri;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getThumbnailUri() {
        return thumbnailUri;
    }

    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }

    public String getFullsizeUri() {
        return fullsizeUri;
    }

    public void setFullsizeUri(String fullsizeUri) {
        this.fullsizeUri = fullsizeUri;
    }
}
