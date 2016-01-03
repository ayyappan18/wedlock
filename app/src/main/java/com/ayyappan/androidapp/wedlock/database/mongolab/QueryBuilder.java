package com.ayyappan.androidapp.wedlock.database.mongolab;

/**
 * Created by Ayyappan on 03/01/2016.
 */
public class QueryBuilder {

    private static final String DATABASE = "wedlock";
    private static final String COUPLE_COLLECTION = "bio";
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


    /**
     * Builds a complete URL using the methods specified above
     * @return
     */
    public String buildContactsSaveURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    /**
     * This method is identical to the one above.
     * @return
     */
    public String buildContactsGetURL()
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    /**
     * Get a Mongodb document that corresponds to the given object id
     * @param doc_id
     * @return
     */
    public String buildContactsUpdateURL(String doc_id)
    {
        return getBaseUrl()+documentRequest()+docApiKeyUrl(doc_id);
    }

}
