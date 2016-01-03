package com.ayyappan.androidapp.wedlock.database;

import android.content.Context;

import com.ayyappan.androidapp.wedlock.model.Bio;
import com.ayyappan.androidapp.wedlock.model.Couple;
import com.ayyappan.androidapp.wedlock.entertainment.bean.Song;
import com.ayyappan.androidapp.wedlock.model.Image;
import com.ayyappan.androidapp.wedlock.model.AppData;
import com.ayyappan.androidapp.wedlock.model.User;
import com.ayyappan.androidapp.wedlock.utils.CoupleProfileJsonReader;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ayyappan on 02/12/2015.
 */
public class MongoDB {

    private static final String DB_URI = "mongodb://app:pass@ds047682.mongolab.com:47682/wedlock";

    private static final String Image_Collection = "images";

    private MongoClientURI mongoClientURI;

    private MongoClient mongoClient;

    private DB db;

    public MongoDB() {
        mongoClientURI = new MongoClientURI("mongodb://app:pass@ds047682.mongolab.com:47682/wedlock");
        try {
            mongoClient = new MongoClient(mongoClientURI);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        db = mongoClient.getDB(mongoClientURI.getDatabase());
    }

    public void sendUser(User user){
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("name", user.getName());
        basicDBObject.put("place", user.getPlace());
        basicDBObject.put("authentication", user.getAuthProvider());
        basicDBObject.put("email", user.getEmail());

        DBCollection songsCollection = db.getCollection("users");
        songsCollection.insert(basicDBObject);
    }
    public List<Image> getImages() {
        List<Image> images = new ArrayList<>();
        DBCollection imagesCollection = db.getCollection("images");
        DBCursor docs = imagesCollection.find();
        while (docs.hasNext()) {
            DBObject doc = docs.next();
            images.add(new Image((Integer) doc.get("imageId"), (String) doc.get("thumbnailUri"), (String) doc.get("fullsizeUri")));
        }
        mongoClient.close();
        return images;
    }


    public AppData getAppData(Context context) {

        DBCollection galleryCollection = db.getCollection("wedlock");
        DBCursor docs = galleryCollection.find();
        AppData result = null;

        while (docs.hasNext()) {
            DBObject doc = docs.next();
            DBObject coupleObject = (DBObject) doc.get("couple");
            Couple couple = getCouple(coupleObject,context);
            BasicDBList imagesList = (BasicDBList) doc.get("images");
            ArrayList<Image> images = getImagesList(imagesList);
            result = new AppData();
            result.setCouple(couple);
            result.setImages(images);
        }

        return result;
    }

    public Couple getCoupleInfo(Context context){
        Couple couple = new Couple();
        DBCollection imagesCollection = db.getCollection("bio");
        DBCursor docs = imagesCollection.find();
        while (docs.hasNext()) {
            DBObject doc = docs.next();
            couple = getCouple(doc,context);
        }
        mongoClient.close();
        return couple;
    }

    private Couple getCouple(DBObject doc,Context context){
        Couple couple = new Couple();

        DBObject brideDbObject = (DBObject)doc.get("bride");
        Bio bride = new Bio((String)brideDbObject.get("name"),(String)brideDbObject.get("picture"), CoupleProfileJsonReader.getResourceId((String)brideDbObject.get("pictureOffline"),context),(String)brideDbObject.get("bio"));
        couple.setBride(bride);

        DBObject groomDbObject = (DBObject)doc.get("groom");
        Bio groom = new Bio((String)groomDbObject.get("name"),(String)groomDbObject.get("picture"),CoupleProfileJsonReader.getResourceId((String)groomDbObject.get("pictureOffline"),context), (String)groomDbObject.get("bio"));

        couple.setGroom(groom);
        return couple;
    }

    private ArrayList<Image> getImagesList(BasicDBList images){
        ArrayList<Image> imagesList = new ArrayList<>();

        for(Iterator<Object> it = images.iterator(); it.hasNext();){
            BasicDBObject doc = (BasicDBObject) it.next();
            imagesList.add(new Image((Integer) doc.get("imageId"), (String) doc.get("thumbnailUri"), (String) doc.get("fullsizeUri")));
        }
        return imagesList;
    }
    public void sendSongInterest(Song song){
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("user", song.getUserName());
        basicDBObject.put("movie", song.getMovieName());
        basicDBObject.put("song", song.getSongName());
        DBCollection songsCollection = db.getCollection("songs");
        songsCollection.insert(basicDBObject);
    }

}
