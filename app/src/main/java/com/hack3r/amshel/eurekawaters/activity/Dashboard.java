package com.hack3r.amshel.eurekawaters.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hack3r.amshel.eurekawaters.R;

public class Dashboard extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("DASHBOARD");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);

    }
}
