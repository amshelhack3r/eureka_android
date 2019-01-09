package com.hack3r.amshel.eurekawaters.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hack3r.amshel.eurekawaters.R;
import com.hack3r.amshel.eurekawaters.helpers.SmsAsync;
import com.hack3r.amshel.eurekawaters.library.Mutall;

public class Dashboard extends AppCompatActivity {
    Toolbar toolbar;
    Mutall mutall;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("DASHBOARD");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        mutall = new Mutall(this);

        requestSmsPermission();
    }

    /**
     * method for generating invoices
     * @param view
     */
    void generate_invoices(View view){
        SmsAsync async = new SmsAsync();
        async.run();
//        mutall.showToast("success", "success");
    }

    /**
     * method for generating schedule in a tabular format
     * @param view
     */
    void generate_schedule(View view){}

    /**
     * Method for adding a new client to the database
     */
    void add_client(View view){}

    /**
     * method for disconnecting a client from the system
     */
    void disconnect_client(View view){}

    /**
     * function for requesting sms permission
     * targeted for android api 23 and above
     */
    void requestSmsPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            //permission not granted
            //show an explanation why
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                mutall.showToast("Please allow permission!", "error");

            } else {

                //request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);

            }
        }
    }
}
