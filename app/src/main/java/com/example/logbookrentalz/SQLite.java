package com.example.logbookrentalz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class SQLite extends SQLiteOpenHelper {
    public SQLite (@Nullable Context context){
        super(context,"AndroidData.db", null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE AndroidDetails(property TEXT PRIMARY KEY, bedroom TEXT, datetime TEXT, price TEXT, furniture TEXT, note TEXT, reporter TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion) {
        db.execSQL("DROP TABLE IF EXISTS AndroidDetails");
    }

    public Boolean insertAndroidData(String property, String bedroom, String datetime, String price, String furniture, String note, String reporter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("property", property);
        contentValues.put("bedroom", bedroom);
        contentValues.put("datetime", datetime);
        contentValues.put("price", price);
        contentValues.put("furniture", furniture);
        contentValues.put("note", note);
        contentValues.put("reporter", reporter);
        long result = db.insert("AndroidDetails", null, contentValues);
        return result != 1;
    }
        public Cursor getData(){
            SQLiteDatabase DB = this.getReadableDatabase();
            Cursor cursor = DB.rawQuery("SELECT * FROM AndroidDetails", null);
            return cursor;
    }
}
