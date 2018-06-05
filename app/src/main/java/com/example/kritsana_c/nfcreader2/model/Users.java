package com.example.kritsana_c.nfcreader2.model;

import android.provider.BaseColumns;

public class Users {

    public static final String DATABASE_NAME = "nfc_users.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "users";


    private int uid;
    private String name;
    private String tagId;


    public class Column {
        public static final String UID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String TAG_ID = "tagId";
    }

    public Users(){

    }

    public Users(int uid, String name, String tagId){
        this.uid = uid;
        this.name = name;
        this.tagId = tagId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return this.uid + " " +this.name+ " " + this.tagId;
    }
}
