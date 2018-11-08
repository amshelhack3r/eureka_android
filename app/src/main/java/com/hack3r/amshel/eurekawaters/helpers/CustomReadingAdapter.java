package com.hack3r.amshel.eurekawaters.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hack3r.amshel.eurekawaters.R;
import com.hack3r.amshel.eurekawaters.objects.Reading;

import java.util.List;
import java.util.Objects;

public class CustomReadingAdapter extends RecyclerView.Adapter<CustomReadingAdapter.MyViewHolder> {
    public List<Reading> reading;

    public CustomReadingAdapter(List<Reading> list) {
        this.reading = list;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView code, name, meter, date, value;

        public MyViewHolder(View view){
            super(view);

            code = view.findViewById(R.id.textView15);
            name = view.findViewById(R.id.textView17);
            meter = view.findViewById(R.id.textView21);
            date = view.findViewById(R.id.textView23);
            value = view.findViewById(R.id.textView24);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_reading, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomReadingAdapter.MyViewHolder holder, int position) {
        Reading r = reading.get(position);
        holder.code.setText(r.getCode());
        holder.name.setText(r.getName());
        holder.meter.setText(r.getMeter());
        holder.date.setText(r.getDate());
        holder.value.setText(String.valueOf(r.getReading()));
    }

    @Override
    public int getItemCount() {
        return reading.size();
    }



}
