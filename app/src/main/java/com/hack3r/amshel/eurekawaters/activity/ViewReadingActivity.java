package com.hack3r.amshel.eurekawaters.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hack3r.amshel.eurekawaters.R;
import com.hack3r.amshel.eurekawaters.helpers.CustomReadingAdapter;
import com.hack3r.amshel.eurekawaters.helpers.DatabaseHandler;
import com.hack3r.amshel.eurekawaters.library.Mutall;
import com.hack3r.amshel.eurekawaters.objects.Reading;

import java.util.List;

public class ViewReadingActivity extends Activity {
    List<Reading> readings;
    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;   Mutall mutall;
    CustomReadingAdapter customReadingAdapter;
    DatabaseHandler databaseHandler;
    EditText criteria;
    Button search;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_readings);
        recyclerView = findViewById(R.id.recycler2);
        refreshLayout = findViewById(R.id.swiperefresh2);
        criteria = findViewById(R.id.editCriteria);
        search = findViewById(R.id.searchCriteria);

        databaseHandler = new DatabaseHandler(this);
        mutall = new Mutall(this);
        populateView("");
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String queryString = criteria.getText().toString();

                if (!queryString.matches("")){
                    populateView(queryString);
                }else {
                    mutall.showToast("no query ", "error");
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateView("");
                clearInputs();
            }
        });

        }

    private void populateView(String query){
        readings = databaseHandler.getAllReadings(query);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        customReadingAdapter = new CustomReadingAdapter(readings);
        recyclerView.setAdapter(customReadingAdapter);
        refreshLayout.setRefreshing(false);

    }

    private void clearInputs(){
        criteria.setText("");
    }

}
