package com.example.newappnews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DbHandler2 extends SQLiteOpenHelper {
    public DbHandler2(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create="CREATE TABLE data (SlNo INTEGER PRIMARY KEY,InNum INTEGER)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = String.valueOf("DROP TABLE IF EXIST");
        db.execSQL(drop,new String[]{"data"});
        onCreate(db);

    }
    public void addData(int SlNo,int InNum){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("SlNo",SlNo);
        values.put("SlNo",InNum);
        long k=db.insert("data",null,values);
        db.close();
    }
    public int getData(int SlNo) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query("data",new String[]{"SlNo","InNum"},"SlNo=?",
                new String[]{String.valueOf(SlNo)},null,null,null);
        if (cursor!=null && cursor.moveToFirst()){
          return cursor.getInt(1);
        }
        else{
            return 999;

        }
    }
//    public void deleteData(int SlNo){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete("data","SlNo=?",new String[]{String.valueOf(SlNo)});
//        db.close();
//    }
}
