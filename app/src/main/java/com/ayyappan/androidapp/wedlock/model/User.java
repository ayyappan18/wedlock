package com.ayyappan.androidapp.wedlock.model;

/**
 * Created by Ayyappan on 15/12/2015.
 */
public class User {
    private String name;
    private String email;
    private String photo;
    private String place;
    private String authProvider;
    private String dateOfLogin;

    public String getName() {
        return name;
    }

    public User(String name, String email, String photo, String place, String authProvider) {
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.place = place;
        this.authProvider = authProvider;
    }

    public User(){ }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(String authProvider) {
        this.authProvider = authProvider;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDateOfLogin() {
        return dateOfLogin;
    }

    public void setDateOfLogin(String dateOfLogin) {
        this.dateOfLogin = dateOfLogin;
    }
}
