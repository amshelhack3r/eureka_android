package com.hack3r.amshel.eurekawaters.objects;

public class Client {
    String code;
    String name;
    String meter;
    String mobile;

    public Client(String c, String n){
        this.code = c;
        this.name = n;
    }

    public Client(String code){
        this.code = code;
    }

    public String getMeter() {
        return meter;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
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
