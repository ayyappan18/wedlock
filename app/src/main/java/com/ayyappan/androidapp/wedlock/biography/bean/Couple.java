package com.ayyappan.androidapp.wedlock.biography.bean;

/**
 * Created by Ayyappan on 05/12/2015.
 */
public class Couple {

    private static Bio bride = null;
    private static Bio groom = null;

    public Bio getBride() {
        return bride;
    }

    public void setBride(Bio bride) {
        Couple.bride = bride;
    }

    public Bio getGroom() {
        return groom;
    }

    public void setGroom(Bio groom) {
        Couple.groom = groom;
    }

}
