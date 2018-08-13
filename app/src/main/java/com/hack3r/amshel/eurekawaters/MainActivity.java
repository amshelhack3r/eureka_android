package com.hack3r.amshel.eurekawaters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Mutall mutall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mutall = new Mutall(this);
    }
    public void monitorMessages(View view){
        Intent i = new Intent(MainActivity.this, Accounts.class);
        startActivity(i);
    }

    public void smsPage(View view){
        Intent intent = new Intent(this, SmsPage.class);
        startActivity(intent);
    }

    public void recordReadings(View view){

        String message = "Hold up lad, Next time it will be ready";
        mutall.showToast(message, "warning");
    }

    public void viewInvoices(View view){
        String message = "Spilt coffee on my laptop. Come back later ";
        mutall.showToast(message, "info");
    }

}
