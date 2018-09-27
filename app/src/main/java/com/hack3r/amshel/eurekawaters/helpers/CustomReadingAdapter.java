package com.hack3r.amshel.eurekawaters.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hack3r.amshel.eurekawaters.R;

import java.util.List;
import java.util.Objects;

public class CustomReadingAdapter extends RecyclerView.Adapter<CustomReadingAdapter.MyViewHolder> {
    public List<Objects> reading;

    public CustomReadingAdapter(List<Objects> list) {
        this.reading = list;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView code, name, mobile, meter, date, value;
        RelativeLayout foreground_view, background_view;

        public MyViewHolder(View view){
            super(view);

            code = view.findViewById(R.id.textView15);
            name = view.findViewById(R.id.textView17);
            meter = view.findViewById(R.id.textView21);
            date = view.findViewById(R.id.textView23);
            mobile = view.findViewById(R.id.textView22);
            value = view.findViewById(R.id.textView24);

            foreground_view = view.findViewById(R.id.view_foreground);
            background_view = view.findViewById(R.id.view_background);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_reading, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomReadingAdapter.MyViewHolder holder, int position) {
//        Object list = reading.get(position);
//        holder.code.setText();

//        List list = reading.get(position);
//        holder.sms.setText(sms.getSmsBody());
//        holder.mobile.setText(sms.getSmsNumber());
    }

    @Override
    public int getItemCount() {
        return reading.size();
    }



}
