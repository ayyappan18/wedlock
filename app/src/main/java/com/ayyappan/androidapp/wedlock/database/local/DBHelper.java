package com.ayyappan.androidapp.wedlock.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ayyappan.androidapp.wedlock.biography.bean.Bio;
import com.ayyappan.androidapp.wedlock.biography.bean.Couple;
import com.ayyappan.androidapp.wedlock.login.bean.User;
import com.sun.mail.imap.protocol.BODY;

import java.util.ArrayList;

/**
 * Created by Ayyappan on 13/12/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Wedlock";
    public static final String GALLERY_TABLE_NAME = "gallery";
    public static final String COUPLE_TABLE_NAME = "couple";

    public static final String KEY_ROWID = "_id";
    public static final String GALLERY_COLUMN_URL = "url";
    public static final String COUPLE_COLUMN_PERSON = "person";;
    public static final String COUPLE_COLUMN_NAME = "name";
    public static final String COUPLE_COLUMN_IMAGE_URL = "image";
    public static final String COUPLE_COLUMN_DETAIL = "detail";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_AUTH_PROVIDER = "authprovider";
    public static final String USER_COLUMN_EMAIL = "email";;
    public static final String USER_COLUMN_PHOTO = "photo";
    public static final String USER_COLUMN_PLACE = "place";

    SQLiteDatabase db;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // TODO Auto-generated method stub
        String CREATE_GALLERY_TABLE = "CREATE TABLE IF NOT EXISTS " + GALLERY_TABLE_NAME + "("
                + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GALLERY_COLUMN_URL + " TEXT NOT NULL UNIQUE)";

        String CREATE_COUPLE_TABLE = "CREATE TABLE IF NOT EXISTS " + COUPLE_TABLE_NAME +
                "(" +
                KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COUPLE_COLUMN_PERSON +" TEXT, " +
                COUPLE_COLUMN_NAME +" TEXT, " +
                COUPLE_COLUMN_IMAGE_URL +" TEXT, " +
                COUPLE_COLUMN_DETAIL + " TEXT " +
                ")";

        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME +
                "(" +
                KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_COLUMN_NAME +" TEXT NOT NULL, " +
                USER_COLUMN_AUTH_PROVIDER +" TEXT NOT NULL, " +
                USER_COLUMN_EMAIL +" TEXT, " +
                USER_COLUMN_PHOTO +" TEXT, " +
                USER_COLUMN_PLACE + " TEXT " +
                ")";

        db.execSQL(CREATE_GALLERY_TABLE);
        db.execSQL(CREATE_COUPLE_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + GALLERY_TABLE_NAME);
        onCreate(db);
    }


    private boolean insertImageUrl  (String url)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GALLERY_COLUMN_URL, url);
        db.insert(GALLERY_TABLE_NAME, null, contentValues);
        return true;
    }

    private boolean insertCouplePerson  (Bio bio, String person, boolean exists)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COUPLE_COLUMN_PERSON, person);
        contentValues.put(COUPLE_COLUMN_NAME, bio.getName());
        contentValues.put(COUPLE_COLUMN_IMAGE_URL, bio.getPictureUrl());
        contentValues.put(COUPLE_COLUMN_DETAIL, bio.getBio());

        if(exists) {
            if (db.update(COUPLE_TABLE_NAME, contentValues, COUPLE_COLUMN_PERSON + " = ", new String[]{person}) > 0) {
                return true;
            }
            else
                return false;
        }
        else {
            if (db.insert(COUPLE_TABLE_NAME, null, contentValues) > 0) {
                return true;
            }
            else
                return false;
        }
    }

    public boolean insertCouple(Couple couple){
        Couple existingCouple = getCouple();
        return insertCouplePerson(couple.getBride(), "bride", existingCouple.getBride() == null) && insertCouplePerson(couple.getGroom(),"groom",existingCouple.getGroom() == null);
    }

    public boolean insertImageUrls(String[] urls) {
        SQLiteDatabase db = this.getWritableDatabase();
    //    db.execSQL("DROP TABLE IF EXISTS "+ GALLERY_TABLE_NAME);
        boolean flag = false;
        for(String url : urls) {
            flag = insertImageUrl(url);
            if(!flag) return false;
        }
        return flag;
    }
/*
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }*/

/*    public boolean updateImageUrl (String[] imageUrls)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(int i=0; i<imageUrls.length; i++)
        contentValues.put(i, imageUrls[i]);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }*/

/*
    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
*/

    public boolean saveUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_NAME, user.getName());
        contentValues.put(USER_COLUMN_AUTH_PROVIDER, user.getAuthProvider());
        contentValues.put(USER_COLUMN_EMAIL, user.getEmail());
        contentValues.put(USER_COLUMN_PHOTO, user.getPhoto());
        contentValues.put(USER_COLUMN_PLACE, user.getPlace());

        if(db.insert(USER_TABLE_NAME, null, contentValues) > 0)
            return true;
        else
            return false;
    }

    public User retrieveUser(){
        // Select All Query
        String selectQuery = "SELECT * FROM " + USER_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery(selectQuery, null );
        res.moveToFirst();

        User user = null;

        while(res.isAfterLast() == false){
           user = new User();
            user.setName(res.getString(res.getColumnIndex(USER_COLUMN_NAME)));
            user.setAuthProvider(res.getString(res.getColumnIndex(USER_COLUMN_AUTH_PROVIDER)));
            user.setEmail(res.getString(res.getColumnIndex(USER_COLUMN_EMAIL)));
            user.setPhoto(res.getString(res.getColumnIndex(USER_COLUMN_PHOTO)));
            user.setPlace(res.getString(res.getColumnIndex(USER_COLUMN_PLACE)));
            return user;
        }
        res.close();
        db.close();
        return user;

    }

    public Couple getCouple()
    {
        Couple couple = new Couple();
        // Select All Query
        String selectQuery = "SELECT * FROM " + COUPLE_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery(selectQuery, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String person = res.getString(res.getColumnIndex(COUPLE_COLUMN_PERSON));
            if( person== "bride"){
                String name = res.getString(res.getColumnIndex(COUPLE_COLUMN_NAME));
                String picture = res.getString(res.getColumnIndex(COUPLE_COLUMN_IMAGE_URL));
                String detail = res.getString(res.getColumnIndex(COUPLE_COLUMN_DETAIL));
                Bio bride = new Bio(name,picture,detail);
                couple.setBride(bride);
            }
            else if (person == "groom"){
                String name = res.getString(res.getColumnIndex(COUPLE_COLUMN_NAME));
                String picture = res.getString(res.getColumnIndex(COUPLE_COLUMN_IMAGE_URL));
                String detail = res.getString(res.getColumnIndex(COUPLE_COLUMN_DETAIL));
                Bio groom = new Bio(name,picture,detail);
                couple.setGroom(groom);
            }
            res.moveToNext();
        }
        res.close();
        db.close();

        if(couple.getGroom() == null || couple.getBride() == null)
            return null;
        else
            return couple;
    }


    public ArrayList<String> getAllImageUrls()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + GALLERY_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery(selectQuery, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(GALLERY_COLUMN_URL)));
            res.moveToNext();
        }
        res.close();
        db.close();
        return array_list;
    }
}