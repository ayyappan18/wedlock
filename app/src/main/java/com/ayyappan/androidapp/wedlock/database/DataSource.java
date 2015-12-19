package com.ayyappan.androidapp.wedlock.database;

import com.ayyappan.androidapp.wedlock.venue.bean.Venue;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ayyappan on 01/11/2015.
 */
public interface DataSource {
    public List<Venue> getVenues(String key) throws IOException, ServiceException;

    public String[] getImagesURLs(String key) throws IOException, ServiceException;
}
