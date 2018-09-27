package com.hack3r.amshel.eurekawaters.objects;

public class Reading {
    private String code;
    private String date;
    private String reading;

    public Reading(String code, String date, String reading){
        this.code = code;
        this.date = date;
        this.reading = reading;
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


}
