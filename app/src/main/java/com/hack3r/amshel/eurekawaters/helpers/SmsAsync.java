package com.hack3r.amshel.eurekawaters.helpers;

import android.app.job.JobService;
import android.telephony.SmsManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.hack3r.amshel.eurekawaters.library.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;

public class SmsAsync implements Runnable {
    String url = "http:mutall.co.ke";
    @Override
    public void run() {
        getMessages();
    }

    /**
     * Get data fro the server
     * This is an asynchronous process
     *
     */
    void getMessages(){
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                sendMessages(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyController.getInstance().addRequestQueue(arrayRequest);
    }

    void sendMessages(JSONArray array){
        SmsManager manager = SmsManager.getDefault();
        for (int i = 0; i>array.length(); i++ ){
            try {
                JSONObject object = array.getJSONObject(i);
                manager.sendTextMessage(object.getString("number"), null, object.getString("message"), null, null);
                delay();
            }catch (JSONException error){
                error.printStackTrace();
            }
        }


    }
    /**
     * function for delyimg 1000 seconds
     */
    void delay() {
        Timer timer = new Timer();

    }
}

