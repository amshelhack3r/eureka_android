package com.hack3r.amshel.eurekawaters;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class SmsPage extends Activity {
    Mutall mutall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smspage);

    }

    /**
     * Create a method for composing a custom message
     *
     * @param view
     */
    public void composeMessage(View view){}

    public void retriveMessage(View view) {}

    /**
     * create a method to request the user to save the custom message to the server
     * Use volley to send the custom message and insert it to the database
     * Return true if the message was inserted and false otherwise
     * @return
     */
    public boolean requestSave(){
        return true;
    }
}
