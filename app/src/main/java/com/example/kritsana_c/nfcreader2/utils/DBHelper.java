package com.example.kritsana_c.nfcreader2.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kritsana_c.nfcreader2.model.History;
import com.example.kritsana_c.nfcreader2.model.Users;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private final String TAG = getClass().getSimpleName();

    private SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context) {
        super(context, Users.DATABASE_NAME, null, Users.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FRIEND_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s TEXT, %s TEXT)",
                Users.TABLE,
                Users.Column.UID,
                Users.Column.NAME,
                Users.Column.TAG_ID
                );
        Log.i(TAG, CREATE_FRIEND_TABLE);
        // create friend table
        db.execSQL(CREATE_FRIEND_TABLE);


        String CREATE_HISTORY_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT)",
                History.TABLE,
                History.Column.UID,
                History.Column.VALUE,
                History.Column.TAG_ID,
                History.Column.DATE
                );
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS " + Users.TABLE;
        String DROP_HISTORY_TABLE = "DROP TABLE IF EXISTS " + History.TABLE;

        db.execSQL(DROP_FRIEND_TABLE);
        db.execSQL(DROP_HISTORY_TABLE);

        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);



        onCreate(db);

    }


    public void addUser(Users user) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(Users.Column.ID, user.getId());
        values.put(Users.Column.NAME, user.getName());
        values.put(Users.Column.TAG_ID, user.getTagId());

        sqLiteDatabase.insert(Users.TABLE, null, values);

        sqLiteDatabase.close();
    }

    //READ
    public Users getUsers(String uid) {

        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query( Users.TABLE,
                null,
                Users.Column.UID + " = ? ",
                new String[] { uid },
                null,
                null,
                null,
                null);


        if (cursor != null) {
            cursor.moveToFirst();
        }

        Users users = new Users();

        users.setUid((int) cursor.getLong(0));
        users.setName(cursor.getString(1));
        users.setTagId(cursor.getString(2));

        return users;
    }

    public Users getUsersByTag(String tag) {

        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query( Users.TABLE,
                null,
                Users.Column.TAG_ID + " = ? ",
                new String[] { tag },
                null,
                null,
                null,
                null);


        if (cursor != null) {
            cursor.moveToFirst();
        }


        Users users = new Users();
        if(cursor.getCount() > 0) {
            users.setUid((int) cursor.getLong(0));
            users.setName(cursor.getString(1));
            users.setTagId(cursor.getString(2));
        }
        return users;
    }
    public List<String> getUsersList() {
        List<String> users = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (Users.TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {

            users.add(cursor.getLong(0) + " " +
                    cursor.getString(1) + " " +
                    cursor.getString(2));

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return users;
    }
    public void addHistory(History history) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(History.Column.VALUE, history.getValue());
        values.put(History.Column.TAG_ID, history.getTagId());
        values.put(History.Column.DATE, history.getDate());

        sqLiteDatabase.insert(History.TABLE, null, values);

        sqLiteDatabase.close();
    }

    public List<String> getHistoryList() {
        List<String> historylist = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (History.TABLE, null, null, null, null, null, null);
        Log.d(TAG, "cursor count: "+ cursor.getCount());

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {

            historylist.add(cursor.getLong(0) + "/" +
                    cursor.getString(1) + "/" +
                    cursor.getString(2)+ "/" +
                    cursor.getString(3));
            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return historylist;
    }

    public List<String> getHistoryListByTagId(String tagId) {
        List<String> historylist = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query( History.TABLE,
                null,
                History.Column.TAG_ID + " = ? ",
                new String[] { tagId },
                null,
                null,
                null,
                null);

        //Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ History.TABLE +
         //                                           " WHERE "+ History.Column.TAG_ID + " = '" + tagId +"'", null);

        Log.d(TAG, "cursor count: "+ cursor.getCount());

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {

            historylist.add(cursor.getLong(0) + " " +
                    cursor.getString(1) + " " +
                    cursor.getString(2)+ "/" +
                    cursor.getString(3));

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return historylist;
    }
}
