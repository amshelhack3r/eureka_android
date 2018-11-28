package com.hack3r.amshel.eurekawaters.helpers;

import android.app.Activity;
import android.telephony.SmsManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.hack3r.amshel.eurekawaters.library.Mutall;
import com.hack3r.amshel.eurekawaters.library.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SmsAsync extends Thread implements Runnable {
    String TAG = SmsAsync.class.getSimpleName();
    String url = "http://mutall.co.ke/eureka_android/client_details.php";
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
                error.printStackTrace();
            }
        });

        VolleyController.getInstance().addRequestQueue(arrayRequest);
    }

    /**
     * create a method for sending sms to each of the clients
     * @param array
     */
    void sendMessages(JSONArray array){
        SmsManager manager = SmsManager.getDefault();
        for (int i = 0; i<array.length(); i++ ){
            try {
                JSONObject object = array.getJSONObject(i);

                ArrayList<String> parts = manager.divideMessage(object.getString("message"));

                String phone_number = object.getString("number");

                manager.sendMultipartTextMessage(phone_number, null, parts, null, null);


                this.sleep(7000);
            }catch (JSONException error){
                error.printStackTrace();
            }catch (InterruptedException error){
                error.printStackTrace();
            }catch (NullPointerException error){
                error.printStackTrace();
            }
        }
    }
}

