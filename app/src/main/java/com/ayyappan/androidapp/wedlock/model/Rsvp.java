package com.ayyappan.androidapp.wedlock.model;

/**
 * Created by Ayyappan on 07/01/2016.
 */
public class Rsvp {

    private String oid;
    private String name;
    private String email;
    private String response;

    public Rsvp(){ }

    public Rsvp(String name, String email, String response) {
        this.name = name;
        this.email = email;
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
