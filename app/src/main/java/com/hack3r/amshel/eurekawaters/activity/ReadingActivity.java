package com.hack3r.amshel.eurekawaters.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.hack3r.amshel.eurekawaters.R;
import com.hack3r.amshel.eurekawaters.helpers.DatabaseHandler;
import com.hack3r.amshel.eurekawaters.helpers.DatePickerFragment;
import com.hack3r.amshel.eurekawaters.library.Mutall;
import com.hack3r.amshel.eurekawaters.library.VolleyController;
import com.hack3r.amshel.eurekawaters.objects.Client;
import com.hack3r.amshel.eurekawaters.objects.Reading;
import com.hack3r.amshel.eurekawaters.query.SqlQuery;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Class for allowing users collect readings and save them to a database
 * It extends the activity class because there will be an interface to actually key in the infomation
 * i implement the DatePickerDialog so as to create a date interface for selecting the current date
 * of reading
 */
public class ReadingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
   //declare my variables
    Mutall mutall;
    DatabaseHandler databaseHandler;
    AutoCompleteTextView code;
    EditText  date, value, meter_edit;
    Button save, clear;
    TextView full_name, prev_date, prev_value, meter_head, meter_value;
    android.support.v7.widget.Toolbar toolbar;
    static final String TAG = ReadingActivity.class.getSimpleName();
    private static final String url = "http://mutall.co.ke/eureka_android/client_details.php";
    private static final String url1 = "http://mutall.co.ke/eureka_android/reading_on_max_date.php";
    private static final String url2 = "http://mutall.co.ke/eureka_android/upload_reading.php";
    List<Client> codes;
    List autocomplete;
    Switch aSwitch, switch2;
    List client_details;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readings);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        //initialize views, and buttons
        full_name = findViewById(R.id.textView19);
        prev_date = findViewById(R.id.textView16);
        prev_value = findViewById(R.id.textView18);
        meter_head = findViewById(R.id.textView10);
        meter_value = findViewById(R.id.textView14);
        code = findViewById(R.id.clientcode);
        date = findViewById(R.id.reading_date);
        value = findViewById(R.id.reading_value);
        save = findViewById(R.id.savebutton);
        clear = findViewById(R.id.clearbutton);
        aSwitch = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        meter_edit = findViewById(R.id.editText);
        autocomplete = new ArrayList();

        code.requestFocusFromTouch();
        date.setFocusable(false);
        //Set an onfocus change listener to the client input so that the client code being input
        //is sent to the server and previous reading, previous date of reading and client name are
        //fetched from the server. This is done when the edit text loses focus.
        //After the data has been fetched it is displayed in the ui

        code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                  client_details = databaseHandler.getReading(code.getText().toString());
                    String client_name = "Name: "+client_details.get(0).toString();
                    full_name.setText(client_name);
                    meter_value.setText(client_details.get(3).toString());
                    if(client_details.get(1) != null && client_details.get(2) != null) {
                        String p_date = "Prev_date: "+client_details.get(1).toString();
                        String p_val = "Prev_read: "+client_details.get(2).toString();

                        prev_date.setText(p_date);
                        prev_value.setText(p_val);
                    }else {
                        prev_date.setText("no prev record");
                        prev_value.setText("no prev record");
                    }
                   }
            }

        });
        //Set a click listener to the on the date edit text to toggle the dateDialog fragment to be
        //displayed
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.DialogFragment datePickerDialog = new DatePickerFragment();
                datePickerDialog.show(getSupportFragmentManager(), "date picker");
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                value.requestFocusFromTouch();
            }
        };
        date.addTextChangedListener(textWatcher);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReadingActivity.this);
                    alertDialog.setTitle("ENTER DEVELOPER MODE");
                    alertDialog.setMessage("Enter Password");

                    final EditText input = new EditText(ReadingActivity.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alertDialog.setView(input);

                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (validatePassword(Integer.valueOf(input.getText().toString()))){
                                        refresh(ReadingActivity.this);
                                    }else{
                                        mutall.showToast( "invalid password","warning");
                                        aSwitch.setChecked(false);
                                    }
                                }
                            });

                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
                }else{
                    refresh(ReadingActivity.this);
                }

                }
            });
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    meter_head.setVisibility(View.INVISIBLE);
                    meter_value.setVisibility(View.INVISIBLE);

                    meter_edit.setText(meter_value.getText().toString());
                    meter_edit.setVisibility(View.VISIBLE);
                }else {
                    meter_edit.setVisibility(View.INVISIBLE);
                    meter_value.setText(meter_edit.getText().toString());
                    meter_value.setVisibility(View.VISIBLE);
                    meter_head.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * create an instance of the mutall class
         * and of the databasehandler class so we can use the methods in them
         */
        mutall = new Mutall(this);
        databaseHandler = new DatabaseHandler(this);
        codes = new ArrayList<>();

        codes = databaseHandler.getAllClients();

        for(Client x : codes){
            String a = x.getCode();
            autocomplete.add(a);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, autocomplete);
        code.setAdapter(arrayAdapter);

        code.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                date.requestFocusFromTouch();
            }
        });
        //save the records to the database. but we validate them first
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String client_code = code.getText().toString();
              String reading_date = date.getText().toString();
              String reading_value = value.getText().toString();
              String meter_no = meter_edit.getText().toString();
                if (client_code == "" || reading_date == "" || reading_value == ""){
                    mutall.showSnack("Please enter all values");
                }else {
                    if (validate(client_code, 1) && validate(reading_date, 2)) {
                        Client client = new Client(client_code, client_details.get(0).toString());
                        client.setMeter(meter_no);

                        databaseHandler.updateMeter(client);
                        Reading reading = new Reading(client_code, reading_date, reading_value);
                        databaseHandler.insertReading(reading);
                        clearInputs();
                    }
                }
            }
        });
        //clear all inputs and all display events
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearInputs();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(aSwitch.isChecked()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();
            switch (id) {
                case R.id.client_count:
                    int x = databaseHandler.getCount(SqlQuery.TABLE_CLIENT);
                    mutall.showToast(String.valueOf(x) + " rows", "info");
                    break;
                case R.id.reading_count:
                    int y = databaseHandler.getCount(SqlQuery.TABLE_READING);
                    mutall.showToast(String.valueOf(y) + " rows", "info");
                    break;
                case R.id.wipe:
                    wipeDB();
                    break;
                case R.id.populateClients:
                    getClientsFromServer(url);
                    break;
                case R.id.populateReadings:
                    getReadingsFromServer(url1);
                    break;
                case R.id.settings:
                    uploadReadings();
            }
            return super.onOptionsItemSelected(item);
    }
    /**
     * Write a method to validate the data being input
     * @param input
     * @param type
     * @return
     */
    public boolean validate(String input, int type){
        Boolean bool = true;
        switch (type){
            case 1:
                if(!autocomplete.contains(input)){
                    bool = false;
                    mutall.showToast("Invalid Code", "warning");
                }
                break;
            case 2:
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date d = simpleDateFormat.parse(input);
                    if(System.currentTimeMillis() < d.getTime()){
                        bool = false;
                        mutall.showToast("Date cant be greater than today's date", "warning");
                    }
                }catch (ParseException e){
                    e.printStackTrace();
                }
                break;
        }
        return bool;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        //The calender class compiles months as arrays so months run from 0-11
        //so we add 1 to the month
        int newMonth = month + 1;
        String myDate = day+"/"+newMonth+"/"+year;
//        String myDate = year+"-"+ newMonth +"-"+day;
        date.setText(myDate);
    }

    public void clearInputs(){
        code.setText("");
        value.setText("");
        full_name.setText("");
        prev_date.setText("");
        prev_value.setText("");
        meter_edit.setText("");
        meter_value.setText("");
        code.requestFocusFromTouch();

    }

    public void refresh(Activity act){
        act.invalidateOptionsMenu();
    }

    private boolean validatePassword(int s){
        if(s == 9375){
            return true;
        }
        return false;
    }

    private void getClientsFromServer(String url){
        //Make a new Json ArrayRequest from server
        JsonArrayRequest clientArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String client_code = jsonObject.getString("code");
                        String client_name = jsonObject.getString("full_name");
                        String client_meter = jsonObject.getString("meter_serial_no");
                        String client_number = jsonObject.getString("number");

                        Client client = new Client(client_code, client_name);
                        if(client_meter != null){
                            client.setMeter(client_meter);
                        }
                        if(client_number != null){
                            client.setMobile(client_number);
                        }

                        databaseHandler.populateClientTable(client);

                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        VolleyController.getInstance().addRequestQueue(clientArrayRequest);
    }

    private void getReadingsFromServer(String url) {
        //Make a new Json ArrayRequest from server
        JsonArrayRequest readingArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String client_code = jsonObject.getString("code");
                        String date = jsonObject.getString("date");
                        String value = jsonObject.getString("value");

                        Reading reading = new Reading(client_code, date, value);

                        databaseHandler.insertReading(reading);

                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        VolleyController.getInstance().addRequestQueue(readingArrayRequest);
    }

    private void uploadReadings(){
        final JSONArray jsonArray = databaseHandler.filteredReadings();

       StringRequest postRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               mutall.showToast(response, "success");
               Log.i(TAG, response);
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Log.e(TAG, error.getMessage());
           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<>();
               params.put("json", jsonArray.toString());
               return params;
           }
       };

       VolleyController.getInstance().addRequestQueue(postRequest);
        //save the filtered readings to a file
        writeToFile(jsonArray.toString(), this);
    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("filter.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    //method for wiping the database
    private void wipeDB(){

        new FancyAlertDialog.Builder(this)
                .setTitle("WIPE THE DATABASE")
                .setBackgroundColor(Color.parseColor("#E91E63"))  //Don't pass R.color.colorvalue
                .setMessage("Do you really want to delete ?")
                .setNegativeBtnText("Cancel")
                .setPositiveBtnBackground(Color.parseColor("#E91E63"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Delete")
                .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.complain, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        int a = databaseHandler.delete(SqlQuery.TABLE_CLIENT);
                        int b = databaseHandler.delete(SqlQuery.TABLE_READING);

                        mutall.showToast(String.valueOf(a)+ " rows deleted", "info");
                        mutall.showToast(String.valueOf(b)+" rows deleted", "info");
                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();

    }
}

