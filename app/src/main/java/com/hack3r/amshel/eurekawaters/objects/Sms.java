package com.hack3r.amshel.eurekawaters.objects;

/**
 * Create an inner class to hold an sms object
 * it will be a simple class that will house the address and body of the sms
 * it will also provide getter and setter methods for the object
 */
public class Sms{

    private String smsBody;
    private String smsNumber;

    public String getSmsBody() {
        return smsBody;
    }

    public String getSmsNumber() {
        return smsNumber;
    }

    public void setSmsBody(String smsBody) {
        this.smsBody = smsBody;
    }

    public void setSmsNumber(String smsNumber) {
        this.smsNumber = smsNumber;
    }
}