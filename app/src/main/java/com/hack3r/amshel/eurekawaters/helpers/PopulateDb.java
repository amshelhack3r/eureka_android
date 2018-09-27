package com.hack3r.amshel.eurekawaters.helpers;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.hack3r.amshel.eurekawaters.library.VolleyController;
import com.hack3r.amshel.eurekawaters.objects.Client;
import com.hack3r.amshel.eurekawaters.objects.Reading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PopulateDb implements Runnable  {
    private DatabaseHandler databaseHandler;
    private Context context;
    private static final String url = "http://mutall.co.ke/eureka_android/client_details.php";
    private static final String url1 = "http://mutall.co.ke/eureka_android/reading_on_max_date.php";
    public PopulateDb(Context c) {
        this.context = c;
        databaseHandler = new DatabaseHandler(context);

    }

    @Override
    public void run() {
        getClientsFromServer(url);
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
                getReadingsFromServer(url1);
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



    }

