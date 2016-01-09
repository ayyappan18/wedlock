package com.ayyappan.androidapp.wedlock.model;

/**
 * Created by Ayyappan on 12/12/2015.
 */
public class Song {
    private String userName;
    private String movieName;
    private String songName;

    public Song(String userName, String movieName, String songName) {
        this.userName = userName;
        this.movieName = movieName;
        this.songName = songName;
    }

    public String getUserName() {
        return userName;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getSongName() {
        return songName;
    }
}
