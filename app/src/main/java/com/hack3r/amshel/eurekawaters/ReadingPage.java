package com.hack3r.amshel.eurekawaters;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReadingPage extends Activity {
    EditText code, date, value;
    Mutall mutall;
    DatabaseHandler databaseHandler;
    Button save, clear;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readings);

        code = findViewById(R.id.appCompatEditText);
        date = findViewById(R.id.appCompatEditText2);
        value = findViewById(R.id.editText);
        save = findViewById(R.id.save);
        clear = findViewById(R.id.clear);
        mutall = new Mutall(this);
        databaseHandler = new DatabaseHandler(this);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String client_code = code.getText().toString();
                String reading_date = date.getText().toString();
                String reading_value = value.getText().toString();

                if(client_code == "" || reading_date == "" || reading_value == ""){
                    mutall.showToast("fill all fields", "warning");

                }else {

                    ReadingObject readingObject = new ReadingObject(1, client_code, reading_date, reading_value);

                    databaseHandler.insertReading(readingObject);
                    mutall.showToast("inserted","success");
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rows = databaseHandler.getCount();
                mutall.showToast("hello", "success");

            }
        });
    }




}
