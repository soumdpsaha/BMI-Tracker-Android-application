package com.example.newbmiapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeightHistoryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weight_history_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "user_weight_history";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public WeightHistoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_WEIGHT + " REAL,"
                + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to add weight record for a user
    public void addWeightRecord(String username, double weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, username);
        values.put(COLUMN_WEIGHT, weight);
        db.insert(TABLE_NAME, null, values);
    }

    // Method to retrieve weight history for a user
    public Cursor getWeightHistory(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_NAME + " = ?";
        return db.rawQuery(query, new String[]{username});
    }

    // Method to delete weight records associated with a user
    public void deleteWeightRecords(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_USER_NAME + " = ?", new String[]{username});
        db.close();
    }

    // You can add more methods as needed, such as updating or deleting weight records

    // ...
}
