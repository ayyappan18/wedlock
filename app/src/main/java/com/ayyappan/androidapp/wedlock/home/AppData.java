package com.ayyappan.androidapp.wedlock.home;

import com.ayyappan.androidapp.wedlock.biography.bean.Couple;
import com.ayyappan.androidapp.wedlock.gallery.bean.Image;

import java.util.List;

/**
 * Created by Ayyappan on 20/12/2015.
 */
public class AppData {
    private static Couple couple;
    private static List<Image> images;

    public AppData() {
    }

    public AppData(Couple couple, List<Image> images){
        this.couple = couple;
        this.images = images;
    }

    public static Couple getCouple() {
        return couple;
    }

    public static void setCouple(Couple couple) {
        AppData.couple = couple;
    }

    public static List<Image> getImages() {
        return images;
    }

    public static void setImages(List<Image> images) {
        AppData.images = images;
    }
}
