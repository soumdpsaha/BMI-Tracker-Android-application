package com.example.newbmiapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bmi_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "user_bmi";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DOB = "date_of_birth";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_WEIGHT = "weight";

    // Make column names public or provide public getters
//    public static String getColumnUser() {
//        return COLUMN_USER_NAME;
//    }

    public static String getColumnName() {
        return COLUMN_NAME;
    }

    public static String getColumnDob() {
        return COLUMN_DOB;
    }

    public static String getColumnHeight() {
        return COLUMN_HEIGHT;
    }

    public static String getColumnWeight() {
        return COLUMN_WEIGHT;
    }

    private WeightHistoryDatabaseHelper weightHistoryDbHelper;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        weightHistoryDbHelper = new WeightHistoryDatabaseHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_USER_NAME + " TEXT PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DOB + " TEXT,"
                + COLUMN_HEIGHT + " REAL,"
                + COLUMN_WEIGHT + " TEXT" // Storing weight array as a JSON string
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    // Add methods to insert, update, delete, and retrieve user data
    // In DatabaseHelper.java

    // Method to check if a user exists
    public boolean userExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Method to add a new user
    public void addUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, username);
        // Add default or empty values for other columns if necessary
        db.insert(TABLE_NAME, null, values);
    }

    // Method to update the height and weight of a user
    public void updateUser(String username, double height, String weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_WEIGHT, weight);
        db.update(TABLE_NAME, values, COLUMN_USER_NAME + " = ?", new String[]{username});
        // Add weight record to weight history database
        weightHistoryDbHelper.addWeightRecord(username, Double.parseDouble(weight));
    }

    // Method to update all user details
    public void updateUserDetails(String username, String name, String dob, double height, String weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DOB, dob);
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_WEIGHT, weight);
        db.update(TABLE_NAME, values, COLUMN_USER_NAME + " = ?", new String[]{username});
        // Add weight record to weight history database
        weightHistoryDbHelper.addWeightRecord(username, Double.parseDouble(weight));
    }

    // Method to retrieve all details of a user
    public Cursor getUserDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_NAME + " = ?";
        return db.rawQuery(query, new String[]{username});
    }

    // Method to get the height of a user
    @SuppressLint("Range")
    public double getHeight(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_HEIGHT + " FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        double height = 0.0; // Default value if no data found

        if (cursor.moveToFirst()) {
            height = cursor.getDouble(cursor.getColumnIndex(COLUMN_HEIGHT));
        }

        cursor.close();
        return height;
    }

    // Method to get the weight of a user
    @SuppressLint("Range")
    public String getWeight(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_WEIGHT + " FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        String weight = ""; // Default value if no data found

        if (cursor.moveToFirst()) {
            weight = cursor.getString(cursor.getColumnIndex(COLUMN_WEIGHT));
        }

        cursor.close();
        return weight;
    }

    // Method to delete a particular row
    public void deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_USER_NAME + " = ?", new String[]{username});

        // Also delete weight records associated with the user from weight history database
        weightHistoryDbHelper.deleteWeightRecords(username);

        db.close();
    }

}

