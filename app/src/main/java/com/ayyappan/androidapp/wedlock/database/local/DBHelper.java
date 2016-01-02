package com.ayyappan.androidapp.wedlock.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ayyappan.androidapp.wedlock.model.Bio;
import com.ayyappan.androidapp.wedlock.model.Couple;
import com.ayyappan.androidapp.wedlock.model.Invitation;
import com.ayyappan.androidapp.wedlock.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public static final String COUPLE_COLUMN_PERSON = "person";
    ;
    public static final String COUPLE_COLUMN_NAME = "name";
    public static final String COUPLE_COLUMN_IMAGE_URL = "image_online";
    public static final String COUPLE_COLUMN_IMAGE_RESOURCE_ID = "image_offline";
    public static final String COUPLE_COLUMN_DETAIL = "detail";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_AUTH_PROVIDER = "authprovider";
    public static final String USER_COLUMN_EMAIL = "email";
    ;
    public static final String USER_COLUMN_PHOTO = "photo";
    public static final String USER_COLUMN_PLACE = "place";

    public static final String INVITATION_TABLE_NAME = "invitation";
    public static final String INVITATION_COLUMN_ID = "id";
    public static final String INVITATION_COLUMN_PASSCODE = "passcode";
    public static final String INVITATION_COLUMN_NAME = "name";

    SQLiteDatabase db;

    public DBHelper(Context context) {
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
                COUPLE_COLUMN_PERSON + " TEXT, " +
                COUPLE_COLUMN_NAME + " TEXT, " +
                COUPLE_COLUMN_IMAGE_URL + " TEXT, " +
                COUPLE_COLUMN_IMAGE_RESOURCE_ID + " NUMBER, " +
                COUPLE_COLUMN_DETAIL + " TEXT " +
                ")";

        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME +
                "(" +
                KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_COLUMN_NAME + " TEXT NOT NULL, " +
                USER_COLUMN_AUTH_PROVIDER + " TEXT NOT NULL, " +
                USER_COLUMN_EMAIL + " TEXT, " +
                USER_COLUMN_PHOTO + " TEXT, " +
                USER_COLUMN_PLACE + " TEXT " +
                ")";

        String CREATE_INVITATION_TABLE = "CREATE TABLE IF NOT EXISTS " + INVITATION_TABLE_NAME +
                "(" +
                INVITATION_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                INVITATION_COLUMN_PASSCODE + " INTEGER, " +
                INVITATION_COLUMN_NAME + " TEXT " +
                ")";


        db.execSQL(CREATE_GALLERY_TABLE);
        db.execSQL(CREATE_COUPLE_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_INVITATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + GALLERY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COUPLE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + INVITATION_TABLE_NAME);
        onCreate(db);
    }


    public boolean insertInvitation(Invitation invitation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INVITATION_COLUMN_ID, invitation.getInvitationId());
        contentValues.put(INVITATION_COLUMN_PASSCODE, invitation.getInvitationPasscode());
        contentValues.put(INVITATION_COLUMN_NAME, invitation.getInvitationName());

        if (db.insert(INVITATION_TABLE_NAME, null, contentValues) > 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }
    }

    public Invitation retrieveInvitation(Integer invitationId) {
        String selectQuery = "SELECT * FROM " + INVITATION_TABLE_NAME + " WHERE " + INVITATION_COLUMN_ID + "=" + invitationId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(selectQuery, null);
        res.moveToFirst();

        Invitation invitation = null;

        while (res.isAfterLast() == false) {
            invitation = new Invitation();
            invitation.setInvitationId(res.getInt(res.getColumnIndex(INVITATION_COLUMN_ID)));
            invitation.setInvitationPasscode(res.getInt(res.getColumnIndex(INVITATION_COLUMN_PASSCODE)));
            invitation.setInvitationName(res.getString(res.getColumnIndex(INVITATION_COLUMN_NAME)));
            return invitation;
        }
        res.close();
        db.close();
        return invitation;

    }


    public Invitation retrieveInvitation() {
        String selectQuery = "SELECT * FROM " + INVITATION_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(selectQuery, null);
        res.moveToFirst();

        Invitation invitation = null;

        while (res.isAfterLast() == false) {
            invitation = new Invitation();
            invitation.setInvitationId(res.getInt(res.getColumnIndex(INVITATION_COLUMN_ID)));
            invitation.setInvitationPasscode(res.getInt(res.getColumnIndex(INVITATION_COLUMN_PASSCODE)));
            invitation.setInvitationName(res.getString(res.getColumnIndex(INVITATION_COLUMN_NAME)));
            return invitation;
        }
        res.close();
        db.close();
        return invitation;

    }

    private boolean insertOrUpdateCouple(Bio bio, String person, boolean exists) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COUPLE_COLUMN_PERSON, person);
        contentValues.put(COUPLE_COLUMN_NAME, bio.getName());
        contentValues.put(COUPLE_COLUMN_IMAGE_URL, bio.getPictureUrl());
        contentValues.put(COUPLE_COLUMN_IMAGE_RESOURCE_ID, bio.getPictureResourceId());
        contentValues.put(COUPLE_COLUMN_DETAIL, bio.getBio());

        if (exists) {
            if (db.update(COUPLE_TABLE_NAME, contentValues, COUPLE_COLUMN_PERSON + " = ", new String[]{person}) > 0) {
                db.close();
                return true;
            } else {
                db.close();
                return false;
            }
        } else {
            if (db.insert(COUPLE_TABLE_NAME, null, contentValues) > 0) {
                db.close();
                return true;
            } else {
                db.close();
                return false;
            }
        }
    }

    public boolean insertCouple(Couple couple) {
        Couple existingCouple = getCouple();
        if (existingCouple == null)
            return insertOrUpdateCouple(couple.getBride(), "bride", false) && insertOrUpdateCouple(couple.getGroom(), "groom", false);
        else
            return insertOrUpdateCouple(couple.getBride(), "bride", true) && insertOrUpdateCouple(couple.getGroom(), "groom", true);
    }

    public boolean insertImageUrls(String[] urls) {
        List<String> newUrls = new ArrayList<>(Arrays.asList(urls));
        newUrls.removeAll(getAllImageUrls());
        SQLiteDatabase db = this.getWritableDatabase();
        boolean flag = false;
        for (String url : newUrls) {

            flag = insertImageUrl(url, db);
            if (!flag) return false;
        }
        db.close();
        return flag;
    }

    private boolean insertImageUrl(String url, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(GALLERY_COLUMN_URL, url);
        db.insert(GALLERY_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean saveUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_NAME, user.getName());
        contentValues.put(USER_COLUMN_AUTH_PROVIDER, user.getAuthProvider());
        contentValues.put(USER_COLUMN_EMAIL, user.getEmail());
        contentValues.put(USER_COLUMN_PHOTO, user.getPhoto());
        contentValues.put(USER_COLUMN_PLACE, user.getPlace());

        if (db.insert(USER_TABLE_NAME, null, contentValues) > 0) {
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }
    }

    public User retrieveUser() {
        // Select All Query
        String selectQuery = "SELECT * FROM " + USER_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(selectQuery, null);
        res.moveToFirst();

        User user = null;

        while (res.isAfterLast() == false) {
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

    public Couple getCouple() {
        Couple couple = new Couple();
        // Select All Query
        String selectQuery = "SELECT * FROM " + COUPLE_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(selectQuery, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            String person = res.getString(res.getColumnIndex(COUPLE_COLUMN_PERSON));
            if (person.equals("bride")) {
                String name = res.getString(res.getColumnIndex(COUPLE_COLUMN_NAME));
                String pictureOnline = res.getString(res.getColumnIndex(COUPLE_COLUMN_IMAGE_URL));
                int pictureOffline = res.getInt(res.getColumnIndex(COUPLE_COLUMN_IMAGE_RESOURCE_ID));
                String detail = res.getString(res.getColumnIndex(COUPLE_COLUMN_DETAIL));
                Bio bride = new Bio(name, pictureOnline, pictureOffline, detail);
                couple.setBride(bride);
            } else if (person.equals("groom")) {
                String name = res.getString(res.getColumnIndex(COUPLE_COLUMN_NAME));
                String pictureOnline = res.getString(res.getColumnIndex(COUPLE_COLUMN_IMAGE_URL));
                int pictureOffline = res.getInt(res.getColumnIndex(COUPLE_COLUMN_IMAGE_RESOURCE_ID));
                String detail = res.getString(res.getColumnIndex(COUPLE_COLUMN_DETAIL));
                Bio groom = new Bio(name, pictureOnline, pictureOffline, detail);
                couple.setGroom(groom);
            }
            res.moveToNext();
        }
        res.close();
        db.close();

        if (couple.getGroom() == null || couple.getBride() == null)
            return null;
        else
            return couple;
    }


    public List<String> getAllImageUrls() {
        List<String> array_list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + GALLERY_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(selectQuery, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(GALLERY_COLUMN_URL)));
            res.moveToNext();
        }
        res.close();
        db.close();
        return array_list;
    }
}