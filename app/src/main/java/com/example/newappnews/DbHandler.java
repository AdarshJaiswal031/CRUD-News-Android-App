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

public class DbHandler extends SQLiteOpenHelper {
    public static int i=0;
    public DbHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create="CREATE TABLE data (SlNo INTEGER PRIMARY KEY,title TEXT,description TEXT, urlToImage TEXT,url TEXT,publishedAt INTEGER)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = String.valueOf("DROP TABLE IF EXIST");
        db.execSQL(drop,new String[]{"data"});
        onCreate(db);

    }
    public void addData(int SlNo,String title , String description , String urlToImage , String url,String publishedAt){
        String arr[]={title,description,urlToImage};
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("SlNo",SlNo);
        values.put("title",title);
        values.put("description",description);
        values.put("urlToImage",urlToImage);
        values.put("url",url);
        values.put("publishedAt",publishedAt);


        long k=db.insert("data",null,values);
        db.close();
    }
    public JSONObject getData(int SlNo) throws JSONException {
        i=SlNo;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query("data",new String[]{"SlNo","title","description","urlToImage","url","publishedAt"},"SlNo=?",
                new String[]{String.valueOf(SlNo)},null,null,null);
           if (cursor!=null && cursor.moveToFirst()){
            JSONObject p=new JSONObject();
            p.put("title",cursor.getString(1));
            p.put("description",cursor.getString(2));
            p.put("urlToImage",cursor.getString(3));
            p.put("url",cursor.getString(4));
            p.put("publishedAt",cursor.getString(5));
            return p;
        }
        else{
            return null;

        }
    }
   public void deleteData(int SlNo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("data","SlNo=?",new String[]{String.valueOf(SlNo)});
        db.close();
   }
   public int getCount(){
        String query = " SELECT * FROM " + "data";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();

   }
}
