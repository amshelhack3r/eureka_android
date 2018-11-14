package com.hack3r.amshel.eurekawaters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.hack3r.amshel.eurekawaters.activity.Dashboard;
import com.hack3r.amshel.eurekawaters.library.Mutall;

public class MainActivity extends AppCompatActivity {
    Mutall mutall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mutall = new Mutall(this);
    }

    void login(View view){
        Intent intent = new Intent(MainActivity.this, Dashboard.class);
        startActivity(intent);
    }

}
