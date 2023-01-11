package com.example.applicationforclassessupport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "login.db";

    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user ( _id INTEGER PRIMARY KEY" + " AUTOINCREMENT, id TEXT, pass TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }



}



//public class DBHelper extends SQLiteOpenHelper {

//    public static final String DBNAME = "login.db";
//    public DBHelper(Context context) {
//        super(context, "login.db", null, 1);
//
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table users(username TEXT primary key, password TEXT)");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table if exists users");
//
//    }
//
//    public Boolean insertData(String username, String password){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("username", username);
//        values.put("password", password);
//
//        long result = db.insert("users", null, values);
//        if(result == 1) return false;
//        else
//            return true;
//    }
//
//    public Boolean checkusername(String username) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from users where username=?", new String[] {username});
//        if(cursor.getCount()>0)
//            return true;
//        else {
//            return false;
//        }
//    }
//
//    public Boolean checkusernamepassword(String username, String password) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[] {username,password});
//        if(cursor.getCount()>0)
//            return true;
//        else
//            return false;
//    }



//}
