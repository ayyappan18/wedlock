package com.ayyappan.androidapp.wedlock.gallery.data;

import com.ayyappan.androidapp.wedlock.biography.bean.Bio;
import com.ayyappan.androidapp.wedlock.biography.bean.Couple;
import com.ayyappan.androidapp.wedlock.entertainment.bean.Song;
import com.ayyappan.androidapp.wedlock.gallery.bean.Image;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.ArrayList;
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
        mongoClient = new MongoClient(mongoClientURI);
        db = mongoClient.getDB(mongoClientURI.getDatabase());
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

    public Couple getCoupleInfo(){
        Couple couple = new Couple();
        DBCollection imagesCollection = db.getCollection("bio");
        DBCursor docs = imagesCollection.find();
        while (docs.hasNext()) {
            DBObject doc = docs.next();
            DBObject brideDbObject = (DBObject)doc.get("bride");
            Bio bride = new Bio((String)brideDbObject.get("name"),(String)brideDbObject.get("picture"),(String)brideDbObject.get("bio"));
            couple.setBride(bride);

            DBObject groomDbObject = (DBObject)doc.get("groom");
            Bio groom = new Bio((String)groomDbObject.get("name"),(String)groomDbObject.get("picture"),(String)groomDbObject.get("bio"));

            couple.setGroom(groom);
        }
        mongoClient.close();
        return couple;
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
