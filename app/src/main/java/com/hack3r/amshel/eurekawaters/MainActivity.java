package com.hack3r.amshel.eurekawaters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ajts.androidmads.sqliteimpex.SQLiteImporterExporter;
import com.hack3r.amshel.eurekawaters.activity.MessagingActivity;
import com.hack3r.amshel.eurekawaters.activity.ReadingActivity;
import com.hack3r.amshel.eurekawaters.activity.SmsActivity;
import com.hack3r.amshel.eurekawaters.library.Mutall;

public class MainActivity extends AppCompatActivity {
    Mutall mutall;
    SQLiteImporterExporter sqLiteImporterExporter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mutall = new Mutall(this);
    }
    public void monitorMessages(View view){
        Intent i = new Intent(MainActivity.this, MessagingActivity.class);
        startActivity(i);
    }

    public void smsPage(View view){
        Intent intent = new Intent(this, SmsActivity.class);
        startActivity(intent);
    }

    public void recordReadings(View view){

//        String message = "Hold up lad, Next time it will be ready";
//        mutall.showToast(message, "warning");
        Intent intent =new Intent(this, ReadingActivity.class);
        startActivity(intent);
    }

    public void viewInvoices(View view){
        String message = "Spilt coffee on my laptop. Come back later ";
        mutall.showToast(message, "info");
    }

}
