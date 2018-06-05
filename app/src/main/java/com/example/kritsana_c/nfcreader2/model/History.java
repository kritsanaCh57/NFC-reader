package com.example.kritsana_c.nfcreader2.model;

import android.provider.BaseColumns;

public class History {
    public static final String DATABASE_NAME = "nfc_users.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "history";

    private int uid;
    private String tagId;
    private String value;



    private String date;

    public class Column {
        public static final String UID = BaseColumns._ID;
        public static final String TAG_ID = "tagId";
        public static final String VALUE = "value";
        public static final String DATE = "date";
    }

    public History(){

    }

    public History(int uid, String tagId, String value, String date){
        this.uid = uid;
        this.tagId = tagId;
        this.value = value;
        this.date = date;
    }
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.uid + " จำนวนเงิน: " + this.value;
    }
}
