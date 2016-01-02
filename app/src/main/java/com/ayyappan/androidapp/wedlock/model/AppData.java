package com.ayyappan.androidapp.wedlock.model;

import com.ayyappan.androidapp.wedlock.model.Couple;
import com.ayyappan.androidapp.wedlock.model.Image;

import java.util.List;

/**
 * Created by Ayyappan on 20/12/2015.
 */
public class AppData {
    private Couple couple;
    private List<Image> images;

    public AppData() {
    }

    public AppData(Couple couple, List<Image> images){
        this.couple = couple;
        this.images = images;
    }

    public Couple getCouple() {
        return couple;
    }

    public void setCouple(Couple couple) {
        this.couple = couple;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
