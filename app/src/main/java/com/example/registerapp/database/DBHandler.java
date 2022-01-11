package com.example.registerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + DBContract.UserDB.TABLE_NAME + " (" + DBContract.UserDB._ID +
            " Integer PRIMARY KEY, " +
            DBContract.UserDB.COLUMN_1 + " TEXT," +
            DBContract.UserDB.COLUMN_2 + " TEXT," +
            DBContract.UserDB.COLUMN_3 + " TEXT," +
            DBContract.UserDB.COLUMN_4 + " TEXT," +
            DBContract.UserDB.COLUMN_5 + " TEXT," +
            DBContract.UserDB.COLUMN_6 + " TEXT)";

    public static final String SQL_DELETE_ENTRIES ="DROP TABLE IF EXISTS " + DBContract.UserDB.TABLE_NAME;

    public long addInfo (String name, String userName, String email, String dob, String password, String gender) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.UserDB.COLUMN_1, name);
        values.put(DBContract.UserDB.COLUMN_2, userName);
        values.put(DBContract.UserDB.COLUMN_3, email);
        values.put(DBContract.UserDB.COLUMN_4, dob);
        values.put(DBContract.UserDB.COLUMN_5, password);
        values.put(DBContract.UserDB.COLUMN_6, gender);

        long rowID = db.insert(DBContract.UserDB.TABLE_NAME, null, values);
        return  rowID;
    }

    public boolean updateInfo (String name, String userName, String email, String dob, String password, String gender) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.UserDB.COLUMN_1, name);
        values.put(DBContract.UserDB.COLUMN_3, email);
        values.put(DBContract.UserDB.COLUMN_4, dob);
        values.put(DBContract.UserDB.COLUMN_5, password);
        values.put(DBContract.UserDB.COLUMN_6, gender);

        String selection = DBContract.UserDB.COLUMN_2 + " LIKE ?";
        String[] selectionArgs = {userName};

        int count = db.update(DBContract.UserDB.TABLE_NAME, values, selection, selectionArgs);
        if (count >= 1) {
            return true;
        }else
            return false;
    }

    public void deleteInfo (String useName) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = DBContract.UserDB.COLUMN_2 + " LIKE ?";
        String[] selectionArgs = {useName};

        db.delete(DBContract.UserDB.TABLE_NAME, selection, selectionArgs);
    }

    public List readInfo(String username) {
        SQLiteDatabase db = getReadableDatabase();
        List user = new ArrayList<>();

        String[] projection = {
                BaseColumns._ID,
                DBContract.UserDB.COLUMN_1,
                DBContract.UserDB.COLUMN_2,
                DBContract.UserDB.COLUMN_3,
                DBContract.UserDB.COLUMN_4,
                DBContract.UserDB.COLUMN_5,
                DBContract.UserDB.COLUMN_6,
        };

        String selection = DBContract.UserDB.COLUMN_2 + " LIKE ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(DBContract.UserDB.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()){
            String name =cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserDB.COLUMN_1));
            String usernm =cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserDB.COLUMN_2));
            String email =cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserDB.COLUMN_3));
            String dob =cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserDB.COLUMN_4));
            String password =cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserDB.COLUMN_5));
            String gender =cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserDB.COLUMN_6));
            user.add(name); //index 0
            user.add(usernm); //index 1 and upwards
            user.add(email);
            user.add(dob);
            user.add(password);
            user.add(gender);
        }
        cursor.close();
        return user;
    }
}
