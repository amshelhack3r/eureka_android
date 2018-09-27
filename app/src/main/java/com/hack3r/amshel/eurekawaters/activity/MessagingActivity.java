package com.hack3r.amshel.eurekawaters.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.hack3r.amshel.eurekawaters.R;
import com.hack3r.amshel.eurekawaters.helpers.CustomSmsAdapter;
import com.hack3r.amshel.eurekawaters.helpers.MyItemTouchHelper;
import com.hack3r.amshel.eurekawaters.library.Mutall;
import com.hack3r.amshel.eurekawaters.library.VolleyController;
import com.hack3r.amshel.eurekawaters.objects.Sms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessagingActivity extends Activity implements MyItemTouchHelper.itemTouchListener {

    private ArrayList numbers;
    private ArrayList<Sms> smsArrayList;
    RecyclerView recyclerView;
    public static final String url = "http://mutall.co.ke/eureka_android/test_intro_message.php";
    Mutall mutall;
    public static final String TAG = MessagingActivity.class.getSimpleName();
    SwipeRefreshLayout refreshLayout;
    HashMap<String, String> params;
    CustomSmsAdapter customSmsAdapter;
    ItemTouchHelper.SimpleCallback simpleCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        recyclerView = findViewById(R.id.recycler);
        refreshLayout = findViewById(R.id.swiperefresh);
        numbers = new ArrayList();
        smsArrayList = new ArrayList<Sms>();
        mutall = new Mutall(this);
        getJsonFromServer(url);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJsonFromServer(url);

            }
        });
    }

    public void promptSend(View view) {
        int no_accounts = numbers.size();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MessagingActivity.this);
        mBuilder.setTitle(R.string.edit_title)
                .setMessage(String.format("You are sending  %1$d messages \n Are you sure?", no_accounts))
                .setPositiveButton(R.string.edit_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mutall.showToast("This is the development version", "info");

                    }
                })
                .setNegativeButton(R.string.edit_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    public void getJsonFromServer(String url) {
        mutall.showProgress("Fetching from server");

        //Make a new Json ArrayRequest from server
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String number = jsonObject.getString("number");
                        String message = jsonObject.getString("message");
                        numbers.add(number);

                        Sms sms = new Sms();
                        sms.setSmsNumber(number);
                        sms.setSmsBody(message);
                        smsArrayList.add(sms);

                    } catch (final JSONException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mutall.showToast(e.getMessage(), "error");
                            }
                        });
                    }
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MessagingActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                recyclerView.setNestedScrollingEnabled(false);
                customSmsAdapter = new CustomSmsAdapter(smsArrayList);
                recyclerView.setAdapter(customSmsAdapter);
                mutall.dismissProgress();

                simpleCallback = new MyItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, MessagingActivity.this);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mutall.dismissProgress();
                Log.e(TAG, error.getMessage());
                mutall.showToast(error.getMessage(), "error");
            }
        });

        VolleyController.getInstance().addRequestQueue(jsonArrayRequest);
        refreshLayout.setRefreshing(false);
    }

    public void postToServer() {
        //Get a list of all numbers gotten from the last activity
        ArrayList sentNumbers = mutall.getAllSentNumbers();

        final JSONArray jsonArray = new JSONArray();


        String url = "http://mutall.co.ke/mutall_eureka_waters/insert/index.php";
        params = new HashMap<>();
        StringRequest anything = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mutall.showToast(response, "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mutall.showSnack(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                params.put("json", jsonArray.toString());
                return params;
            }
        };
        VolleyController.getInstance().addRequestQueue(anything);
    }

    @Override
    public void onSwipped(RecyclerView.ViewHolder viewHolder, int Directions, int postion) {
        if (viewHolder instanceof CustomSmsAdapter.MyViewHolder) {
            if (Directions == ItemTouchHelper.LEFT) {
                // get the removed item name to display it in snack bar
                String name = smsArrayList.get(viewHolder.getAdapterPosition()).getSmsNumber();

                // backup of removed item for undo purpose
                final Sms deletedItem = smsArrayList.get(viewHolder.getAdapterPosition());
                final int deletedIndex = viewHolder.getAdapterPosition();

                // remove the item from recycler view
                customSmsAdapter.removeItem(viewHolder.getAdapterPosition());

                View view = this.findViewById(R.id.accounts_view);
                // showing snack bar with Undo option
                final Snackbar snackbar = Snackbar
                        .make(view, name + " removed from list!", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("UNDO DELETED ITEM", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // undo is selected, restore the deleted item
                        customSmsAdapter.restoreItem(deletedItem, deletedIndex);
                        snackbar.dismiss();
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            } else {
                String number = smsArrayList.get(viewHolder.getAdapterPosition()).getSmsNumber();
                String message = smsArrayList.get(viewHolder.getAdapterPosition()).getSmsBody();
                //send the sms
//                mutall.send_sms(number, message);
                mutall.showToast(number+ "\n"+ message, "success" );
            }
        }
    }
}