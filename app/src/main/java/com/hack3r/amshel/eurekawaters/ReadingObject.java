package com.hack3r.amshel.eurekawaters;

public class ReadingObject {

    public static final String Table_Name = "reading";
    public static String COLUMN_ID = "reading";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_VALUE = "value";


    public static String CREATE_TABLE = "CREATE TABLE "
            +Table_Name +"("
                    +COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +COLUMN_CODE +" TEXT NOT NULL UNIQUE, "
                    +COLUMN_DATE +" TEXT, "
                    +COLUMN_VALUE+" TEXT NOT NULL)";

    public static String DROP_TABLE ="DROP TABLE IF EXISTS "+Table_Name;

    private String code;
    private String date;
    private String reading;
    private int _id;

    public ReadingObject(int _id, String code, String date, String reading){
        this.code = code;
        this.date = date;
        this.reading = reading;
        this._id = _id;
    }

    public int getId(){
        return _id;
    }

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public String getReading() {
        return reading;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
