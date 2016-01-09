package com.ayyappan.androidapp.wedlock.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;


/**
 * Created by Ayyappan on 01/11/2015.
 */
public class Venue implements Parcelable{

    private String eventName;
    private DateTime eventStartDate;
    private DateTime eventEndDate;
    private String venueName;
    private String hallName;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private Double coordinateLat;
    private Double coordinateLong;
    public Venue(){
        super();
    }

    public Venue(String eventName, DateTime eventStartDate, DateTime eventEndDate, String venueName, String hallName, String addressLine1, String addressLine2, String addressLine3, Double coordinateLat, Double coordinateLong){
        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.venueName = venueName;
        this.hallName = hallName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.coordinateLat = coordinateLat;
        this.coordinateLong = coordinateLong;
    }

    private Venue(Parcel in) {
        super();
        this.eventName = in.readString();
        this.eventStartDate = new DateTime(in.readString());
        this.eventEndDate = new DateTime(in.readString());
        this.venueName = in.readString();
        this.hallName = in.readString();
        this.addressLine1 = in.readString();
        this.addressLine2 = in.readString();
        this.addressLine3 = in.readString();
        this.coordinateLat = in.readDouble();
        this.coordinateLong = in.readDouble();

    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(getEventName());
        parcel.writeString(getEventStartDate().toString());
        parcel.writeString(getEventEndDate().toString());
        parcel.writeString(getVenueName());
        parcel.writeString(getHallName());
        parcel.writeString(getAddressLine1());
        parcel.writeString(getAddressLine2());
        parcel.writeString(getAddressLine3());
        parcel.writeDouble(getCoordinateLat());
        parcel.writeDouble(getCoordinateLong());

    }

    public static final Parcelable.Creator<Venue> CREATOR = new Parcelable.Creator<Venue>() {
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public DateTime getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(DateTime eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public DateTime getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(DateTime eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String name) {
        this.venueName = name;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public Double getCoordinateLat() {
        return coordinateLat;
    }

    public void setCoordinateLat(Double coordinate) {
        this.coordinateLat = coordinate;
    }

    public Double getCoordinateLong() {
        return coordinateLong;
    }

    public void setCoordinateLong(Double coordinate) {
        this.coordinateLong = coordinate;
    }

    @Override
    public String toString() {
        return "Venue [EventName=" + eventName + ", venueName=" + venueName + ", AddressLine1="
                + addressLine1 + "]";
    }
}
