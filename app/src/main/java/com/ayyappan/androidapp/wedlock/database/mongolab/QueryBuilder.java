package com.ayyappan.androidapp.wedlock.database.mongolab;

import com.ayyappan.androidapp.wedlock.model.Rsvp;
import com.ayyappan.androidapp.wedlock.model.Song;
import com.ayyappan.androidapp.wedlock.model.User;

/**
 * Created by Ayyappan on 03/01/2016.
 */
public class QueryBuilder {

    private static final String DATABASE = "wedlock";
    private static final String COUPLE_COLLECTION = "bio";
    private static final String USERS = "users";
    private static final String SONGS = "songs";
    private static final String RSVP = "rsvp";
    private static final String GALLERY_COLLECTION = "images";
    private static final String APIKEY = "m74mI8v69PkdV4Xlcn-tJpX2jCF4MEtF";
    private static final String APP_DETAILS_COLLECTION = "wedlock";

    /**
     * Specify your database name here
     * @return
     */
    public String getDatabaseName() {
        return DATABASE;
    }

    /**
     * Specify your MongoLab API here
     * @return
     */
    public String getApiKey() {
        return APIKEY;
    }

    /**
     * This constructs the URL that allows you to manage your database,
     * collections and documents
     * @return
     */
    public String getBaseUrl()
    {
        return "https://api.mongolab.com/api/1/databases/"+getDatabaseName()+"/collections/";
    }

    /**
     * Completes the formating of your URL and adds your API key at the end
     * @return
     */
    public String docApiKeyUrl()
    {
        return "?apiKey="+getApiKey();
    }

    /**
     * Get a specified document
     * @param docid
     * @return
     */
    public String docApiKeyUrl(String docid)
    {
        return "/"+docid+"?apiKey="+getApiKey();
    }

    /**
     * Returns the docs101 collection
     * @return
     */
    public String documentRequest()
    {
        return "docs101";
    }

    public String coupleCollecionRequest() { return COUPLE_COLLECTION;}

    public String usersCollecionRequest() { return USERS;}

    public String rsvpCollecionRequest() { return RSVP;}

    public String songsCollecionRequest() { return SONGS;}

    public String appDetailsCollecionRequest() { return APP_DETAILS_COLLECTION;}

    public String galleryCollecionRequest() { return GALLERY_COLLECTION;}

    /**
     * This method is identical to the one above.
     * @return
     */
    public String buildCoupleProfileGetURL()
    {
        return getBaseUrl()+coupleCollecionRequest()+docApiKeyUrl();
    }

    public String buildAppDetailsGetURL()
    {
        return getBaseUrl()+appDetailsCollecionRequest()+docApiKeyUrl();
    }

    public String buildGalleryGetURL()
    {
        return getBaseUrl()+galleryCollecionRequest()+docApiKeyUrl();
    }


    public String buildUserSaveURL()
    {
        return getBaseUrl()+usersCollecionRequest()+docApiKeyUrl();
    }

    public String buildRsvpResponseURL()
    {
        return getBaseUrl()+rsvpCollecionRequest()+docApiKeyUrl();
    }

    public String buildRsvpResponseUpdateURL(String doc_id)
    {
        return getBaseUrl()+rsvpCollecionRequest()+docApiKeyUrl(doc_id);
    }

    public String buildSongSaveURL()
    {
        return getBaseUrl()+songsCollecionRequest()+docApiKeyUrl();
    }

    public String createUser(User user)
    {
        return String
                .format("{\"name\": \"%s\", "
                                + "\"email\": \"%s\", \"place\": \"%s\", \"authProdiver\": \"%s\", "
                                + "\"date\": \"%s\"}",
                        user.getName(), user.getEmail(),user.getPlace(),user.getAuthProvider(),user.getDateOfLogin());
    }


    public String createSong(Song song)
    {
        return String
                .format("{\"movie\": \"%s\", "
                                + "\"song\": \"%s\", "
                                + "\"user\": \"%s\"}",
                        song.getMovieName(), song.getSongName(),song.getUserName());
    }

    public String createRsvp(Rsvp rsvp)
    {
        return String
                .format("{\"name\": \"%s\", "
                                + "\"email\": \"%s\", "
                                + "\"response\": \"%s\"}",
                        rsvp.getName(), rsvp.getEmail(), rsvp.getResponse());
    }

    public String updateRsvp(Rsvp rsvp)
    {
        return String
                .format("{ \"$set\" : "
                        + "{\"name\": \"%s\", "
                        + "\"email\": \"%s\", "
                        + "\"response\": \"%s\"}" + "}",
                        rsvp.getName(), rsvp.getEmail(), rsvp.getResponse());
    }
}
