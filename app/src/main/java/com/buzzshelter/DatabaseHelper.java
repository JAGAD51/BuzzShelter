package com.buzzshelter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.buzzshelter.Model.AccountType;
import com.buzzshelter.Model.User;
import com.buzzshelter.Model.Shelter;
import com.buzzshelter.Model.AccountType;
import android.database.Cursor;
import android.widget.Toast;

/**
 * Created by user on 25/03/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //DB Version
    private static final int DATABASE_VERSION = 1;

    //db name
    private static final String DATABASE_NAME = "buzzshelterdb";

    //table names
    private static final String TABLE_USER = "users";
    private static final String TABLE_SHELTERS = "shelters";

    //shared-FKs
    private static final String KEY_SHELTER_NAME = "shelter_name";

    //USERS Table - column names
    private static final String KEY_USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_PASSWORD = "user password";
    private static final String USER_ACCOUNT_TYPE = "user_account_type";
    private static final String USER_NUMBER_BEDS_CLAIMED = "user_number_beds_claimed";

    //SHELTERS Table - column names
    private static final String SHELTER_CAPACITY = "shelter_capacity";
    private static final String SHELTER_RESTRICTIONS = "shelter_restrictions";
    private static final String SHELTER_LONGITUDE = "shelter_longitude";
    private static final String SHELTER_LATITUDE = "shelter_latitude";
    private static final String SHELTER_ADDRESS = "shelter_address";
    private static final String SHELTER_PHONE_NUMBER = "shelter_phone_number";
    private static final String SHELTER_VACANCY = "shelter_vacancy";


    //Table Create Statements

    //create table user
    //TODO: check for valid entries!
    //TODO: enum checkk will be  pType TEXT CHECK( USER_ACCOUNT_TYPE IN ('Admin', 'User') ) NOT NULL DEFAULT 'User',
    //TODO: check integrity of foreign key
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USER + "(" +
            KEY_USER_ID + " TEXT PRIMARY KEY," +
            USER_NAME + " TEXT, " +
            USER_PASSWORD + " TEXT, " +
            USER_ACCOUNT_TYPE + " TEXT, " +
            USER_NUMBER_BEDS_CLAIMED + " INTEGER, " +
            KEY_SHELTER_NAME + " TEXT " +
            ")";

    //create table shelters
    //TODO: check for valid entries!
    //TODO: enum checkk will be  pType TEXT CHECK( USER_ACCOUNT_TYPE IN ('Admin', 'User') ) NOT NULL DEFAULT 'User',
    private static final String CREATE_TABLE_SHELTERS = "CREATE TABLE" + TABLE_SHELTERS + "(" +
            KEY_SHELTER_NAME + "TEXT PRIMARY KEY" +
            SHELTER_CAPACITY + " TEXT, " +
            SHELTER_RESTRICTIONS + " TEXT, " +
            SHELTER_LONGITUDE + " TEXT, " +
            SHELTER_LATITUDE + " TEXT, " +
            SHELTER_ADDRESS + " TEXT, " +
            SHELTER_PHONE_NUMBER + " TEXT, " +
            SHELTER_VACANCY + " INTEGER " +
            ")";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        sqldb.execSQL(CREATE_TABLE_USERS);
        sqldb.execSQL(CREATE_TABLE_SHELTERS);

    }

    //DANGER. DROPS ALL TABLES
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHELTERS);

        onCreate(db);

    }

    //CRUDs

    //CRUD USER
    //create an entry in the USER table
    public long createUSER(User user, Shelter taken_Shelter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, user.getName());
        values.put(USER_NAME, user.getName());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_ACCOUNT_TYPE, user.getAccountType().toString());
        values.put(USER_NUMBER_BEDS_CLAIMED, user.getNumberBedClaimed());
        values.put(KEY_SHELTER_NAME, taken_Shelter.getName());

        //Insert Row
        long user_row = db.insert(CREATE_TABLE_USERS, null, values);

        return user_row;


    }

    //fetch unqiue user
    //there are NOT unique user instances
    public User fetchSpecificUserByID(String user_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " +
                KEY_USER_ID + " = " + user_id;
        Cursor c = db.rawQuery(selectQuery, null);
        if(c != null) {
            c.moveToFirst();
        }
        User us = new User(c.getString(c.getColumnIndex(KEY_USER_ID)), c.getString(c.getColumnIndex(USER_NAME)),
                c.getString(c.getColumnIndex(USER_PASSWORD)),
                AccountType.valueOf(c.getString(c.getColumnIndex(USER_ACCOUNT_TYPE))));
        us.setNumberBedClaimed(c.getInt(c.getColumnIndex(USER_NUMBER_BEDS_CLAIMED)));
        us.setLocationBedClaimed(c.getString(c.getColumnIndex(KEY_SHELTER_NAME)));
        c.close();
        return us;
    }

    //TODO: LIST OF USERS


    //TODO: ASK TEAM ABOUT SPECIFIC QUERIES WANTED

    //Update User
    public int updateUSER(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, user.getName());
        values.put(USER_NAME, user.getName());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_ACCOUNT_TYPE, user.getAccountType().toString());
        values.put(USER_NUMBER_BEDS_CLAIMED, user.getNumberBedClaimed());
        values.put(KEY_SHELTER_NAME, user.getLocationBedClaimed());

        //updating row
        //returns the number of rows affected
        return db.update(TABLE_USER, values, KEY_USER_ID + " = ?",
                new String[] {user.getId()});

    }

    //Delete User
    public void deleteUSER(String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_USER_ID + " = ?",
                new String[] {user_id});

    }



    //CRUD SHELTER
    //create an entry in the SHELTER table
    public long createSHELTER(Shelter shelter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SHELTER_NAME, shelter.getName());
        values.put(SHELTER_CAPACITY, shelter.getCapacity());
        values.put(SHELTER_RESTRICTIONS, shelter.getRestrictions());
        values.put(SHELTER_LONGITUDE, shelter.getLongitude());
        values.put(SHELTER_LATITUDE, shelter.getLatitude());
        values.put(SHELTER_ADDRESS, shelter.getAddress());
        values.put(SHELTER_PHONE_NUMBER, shelter.getPhoneNumber());
        values.put(SHELTER_VACANCY, shelter.getVacancy());

        //Insert Row
        long shelter_row = db.insert(TABLE_SHELTERS, null, values);

        return shelter_row;
    }

    //fetch unique shelter
    //there are NOT unique shelter instances
    public Shelter fetchSpecificShelterByName(String shelter_name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SHELTERS + " WHERE " +
                KEY_SHELTER_NAME + " = " + shelter_name;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null) {
            c.moveToFirst();
        }

        Shelter s = new Shelter(c.getString(c.getColumnIndex(KEY_SHELTER_NAME)),
                c.getString(c.getColumnIndex(SHELTER_CAPACITY)),
                c.getString(c.getColumnIndex(SHELTER_RESTRICTIONS)),
                c.getString(c.getColumnIndex(SHELTER_LONGITUDE)),
                c.getString(c.getColumnIndex(SHELTER_LATITUDE)),
                c.getString(c.getColumnIndex(SHELTER_ADDRESS)),
                c.getString(c.getColumnIndex(SHELTER_PHONE_NUMBER))
                );
        s.setVacancy(c.getInt(c.getColumnIndex(SHELTER_VACANCY)));

        c.close();

        return s;
    }

    //TODO: LIST OF SHELTERS
    //TODO:


    //updating a shelter

    public long updateSHELTER(Shelter shelter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SHELTER_NAME, shelter.getName());
        values.put(SHELTER_CAPACITY, shelter.getCapacity());
        values.put(SHELTER_RESTRICTIONS, shelter.getRestrictions());
        values.put(SHELTER_LONGITUDE, shelter.getLongitude());
        values.put(SHELTER_LATITUDE, shelter.getLatitude());
        values.put(SHELTER_ADDRESS, shelter.getAddress());
        values.put(SHELTER_PHONE_NUMBER, shelter.getPhoneNumber());
        values.put(SHELTER_VACANCY, shelter.getVacancy());

        //updating row
        //returns the number of rows affected
        return db.update(TABLE_SHELTERS, values, KEY_USER_ID + " = ?",
                new String[]{shelter.getName()});
    }

    //delete shelter
    public void deleteSHELTER(String shelter_name) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SHELTERS, KEY_SHELTER_NAME + " = ?",
                new String[] {shelter_name});

    }



    //closing the database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen()) {
            db.close();
        }
        else {
            System.out.println("Database was not open to begin with");
        }
    }


}








































