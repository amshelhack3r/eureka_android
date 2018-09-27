package com.hack3r.amshel.eurekawaters.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.hack3r.amshel.eurekawaters.objects.Client;
import com.hack3r.amshel.eurekawaters.query.SqlQuery;
import com.hack3r.amshel.eurekawaters.objects.Reading;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            sqLiteDatabase.execSQL(SqlQuery.CREATE_CLIENT_TABLE);
            sqLiteDatabase.execSQL(SqlQuery.CREATE_READING_TABLE);
            sqLiteDatabase.execSQL(SqlQuery.CLIENT_INDEXES);
            sqLiteDatabase.execSQL(SqlQuery.READING_INDEXES);
        }catch (SQLiteException e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
            }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL(SqlQuery.DROP_TABLE_CLIENT);
            sqLiteDatabase.execSQL(SqlQuery.DROP_TABLE_READING);
            onCreate(sqLiteDatabase);
        }catch (SQLiteException e ){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }

    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    /**
     * method for inserting a reading
     */
    public void insertReading(Reading reading){

        Date date;
        //get writeable database as we want to write to the database
        SQLiteDatabase database = this.getWritableDatabase();
        try {
             database.execSQL(SqlQuery.INSERT_READING, new Object[]{reading.getDate(), reading.getReading(), reading.getCode()});
        }catch (SQLiteException e){
            e.printStackTrace();
        }
            database.close();

    }

    public List getReading(String code){
        List list = new ArrayList();
        if(code != null) {

            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery(SqlQuery.SELECT_PREVIOUS_READING, new String[]{code});
            if (cursor != null)
                cursor.moveToFirst();
            try {

                String name = cursor.getString(cursor.getColumnIndex(SqlQuery.CLIENT_COLUMN_NAME));
                String date = cursor.getString(cursor.getColumnIndex(SqlQuery.READING_COLUMN_DATE));
                String value = cursor.getString(cursor.getColumnIndex(SqlQuery.READING_COLUMN_VALUE));
                String meter = cursor.getString(cursor.getColumnIndex(SqlQuery.CLIENT_COLUMN_METER));
                list.add(name);
                list.add(date);
                list.add(value);
                list.add(meter);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
                cursor.close();
        }
            return list;

        }

//    public List<Reading> getAllReadings(){
//        List<Reading> readings = new ArrayList<>();
//
//        SQLiteDatabase database = this.getWritableDatabase();
//
//        String sql = "SELECT * FROM "+ SqlQuery.TABLE_READING;
//
//        Cursor cursor = database.rawQuery(sql, null);
//
//        if(cursor.moveToFirst()){
//            do {
//                Reading reading = new Reading(
//                        cursor.getString(cursor.getColumnIndex(COLUMN_CODE)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
//                        cursor.getString(cursor.getColumnIndex(COLUMN_VALUE))
//                );
//                readings.add(reading);
//            }while (cursor.moveToNext());
//        }
//        database.close();
//
//        return readings;
//     }
//  get all clients
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(SqlQuery.SELECT_GROUP_CLIENTS, null);


        if (cursor.moveToFirst()) {
            do {
                Client client = new Client(
                        cursor.getString(cursor.getColumnIndex(SqlQuery.CLIENT_COLUMN_CODE)),
                        cursor.getString(cursor.getColumnIndex(SqlQuery.CLIENT_COLUMN_NAME))
                );
                clients.add(client);
            } while (cursor.moveToNext());
        }
        database.close();

        return clients;
    }

      public int delete(String Table){
        SQLiteDatabase database = this.getWritableDatabase();

        int rows_deleted = database.delete(Table, null, null);
        return rows_deleted;
    }

    public int getCount(String Table){
        SQLiteDatabase database = this.getWritableDatabase();

        String sql = "SELECT * FROM "+ Table;

        Cursor cursor = database.rawQuery(sql, null);

        int rowCount = cursor.getCount();

        return rowCount;
    }

    public int updateMeter(Client client){
        //get writeable database as we want to write to the database
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SqlQuery.CLIENT_COLUMN_METER, client.getMeter());

        int id = database.update(SqlQuery.TABLE_CLIENT, values, "code = ?", new String[]{client.getCode()});
        database.close();
        return id;
    }

    public void populateClientTable(Client client){
        //get writeable database as we want to write to the database
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            database.execSQL(SqlQuery.INSERT_CLIENTS, new Object[]{client.getCode(), client.getName(), client.getMeter(), client.getMobile()});
        }catch(SQLiteException e){
            e.printStackTrace();
        }
        database.close();
    }

    public String populateReadingTable(Reading reading){
        String message;
        //get writeable database as we want to write to the database
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            database.execSQL(SqlQuery.INSERT_READING, new Object[]{reading.getDate(), reading.getReading(), reading.getCode()});
            message = "Record Inserted";
        }catch (SQLiteException e){
            message = e.getMessage();
        }
        database.close();
        return message;
    }

    public JSONArray filteredReadings(){

        JSONArray jsonArray = new JSONArray();

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(SqlQuery.DAILY_READING_POSTING, new String[]{"2018-%"});

        if (cursor.moveToFirst()) {
            do {
                try {
                    JSONObject jsonObject = new JSONObject();
                    String code = cursor.getString(cursor.getColumnIndex(SqlQuery.CLIENT_COLUMN_CODE));
                    String name = cursor.getString(cursor.getColumnIndex(SqlQuery.CLIENT_COLUMN_NAME));
                    String meter = cursor.getString(cursor.getColumnIndex(SqlQuery.CLIENT_COLUMN_METER));
                    String mobile = cursor.getString(cursor.getColumnIndex(SqlQuery.CLIENT_COLUMN_TELEPHONE));
                    String date = cursor.getString(cursor.getColumnIndex(SqlQuery.READING_COLUMN_DATE));
                    String value = cursor.getString(cursor.getColumnIndex(SqlQuery.READING_COLUMN_VALUE));

                    jsonObject.put("code", code);
                    jsonObject.put("name", name);
                    jsonObject.put("meter", meter);
                    jsonObject.put("mobile", mobile);
                    jsonObject.put("date", date);
                    jsonObject.put("value", value);
                    jsonArray.put(jsonObject);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }
        database.close();
        return jsonArray;
    }


}
