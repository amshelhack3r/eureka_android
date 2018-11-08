package com.hack3r.amshel.eurekawaters.objects;

public class Reading {
    private String code;
    private String date;
    private int reading;
    private String name;
    private String meter;
    private String mobile;

    public Reading(String code, String date, int reading){
        this.code = code;
        this.date = date;
        this.reading = reading;

    }
    public Reading(String c, String n){
        this.code = c;
        this.name = n;
    }

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public int getReading() {
        return reading;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setReading(int reading) {
        this.reading = reading;
    }

    public String getMeter() {
        return meter;
    }

    public String getMobile() {
        return mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
