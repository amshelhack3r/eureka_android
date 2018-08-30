package com.hack3r.amshel.eurekawaters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.hack3r.amshel.eurekawaters.ReadingObject.COLUMN_CODE;
import static com.hack3r.amshel.eurekawaters.ReadingObject.COLUMN_DATE;
import static com.hack3r.amshel.eurekawaters.ReadingObject.COLUMN_ID;
import static com.hack3r.amshel.eurekawaters.ReadingObject.COLUMN_VALUE;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mutall_eureka_waters";
    private static final int DATABASE_VERSION = 1;
    private final String TAG = DatabaseHandler.class.getSimpleName();

    public DatabaseHandler(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(ReadingObject.CREATE_TABLE);
        }catch (SQLiteException e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
            }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL(ReadingObject.DROP_TABLE);
            onCreate(sqLiteDatabase);
        }catch (SQLiteException e ){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * method for inserting a readingObject
     */
    public long insertReading(ReadingObject readingObject){
        //get writeable database as we want to write to the database
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_CODE, readingObject.getCode());
        values.put(COLUMN_DATE, readingObject.getDate());
        values.put(COLUMN_VALUE, readingObject.getReading());

        long id = database.insert(ReadingObject.Table_Name, null, values);

        database.close();
        return id;
    }

    public ReadingObject getReading(long id){
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * from "+ ReadingObject.Table_Name +" WHERE id = "+id;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null)
            cursor.moveToFirst();

        ReadingObject readingObject = new ReadingObject(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CODE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_VALUE))
            );

            cursor.close();

        return readingObject;
    }

    public List<ReadingObject> getAllReadings(){
        List<ReadingObject> readingObjects = new ArrayList<>();

        SQLiteDatabase database = this.getWritableDatabase();

        String sql = "SELECT * FROM "+ ReadingObject.Table_Name;

        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do {
                ReadingObject readingObject = new ReadingObject(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CODE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_VALUE))
                );
                readingObjects.add(readingObject);
            }while (cursor.moveToNext());
        }
        database.close();

        return readingObjects;
     }

     public int getCount(){
         SQLiteDatabase database = this.getWritableDatabase();

         String sql = "SELECT * FROM "+ ReadingObject.Table_Name;

         Cursor cursor = database.rawQuery(sql, null);

         int rowCount = cursor.getCount();

         return rowCount;
     }

}
