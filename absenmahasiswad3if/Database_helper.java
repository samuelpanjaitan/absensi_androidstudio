package com.dif.bimo.absenmahasiswad3if;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database_helper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mhs.db";
    private static final String TABLE_NAME = "mahasiswa";

    public Database_helper(Context context) {
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + "("
                + "nim TEXT PRIMARY KEY,"
                + "nama TEXT"+ ")";
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public void insertData(ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null,values);
        db.close();
    }


    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT nim AS _id, nama FROM " + TABLE_NAME;

        return db.rawQuery(selectQuery,null);
    }

}
