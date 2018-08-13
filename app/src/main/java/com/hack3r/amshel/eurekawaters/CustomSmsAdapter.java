package com.hack3r.amshel.eurekawaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class CustomSmsAdapter extends RecyclerView.Adapter<CustomSmsAdapter.MyViewHolder> {
    public List<Sms> smsList;

    public CustomSmsAdapter(List<Sms> list) {
        this.smsList = list;

    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView sms, mobile;
        ImageView image;

        public MyViewHolder(View view){
            super(view);

//            image = view.findViewById(R.id.client_image);
            sms = view.findViewById(R.id.sms);
            mobile = view.findViewById(R.id.mobile);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Sms sms = smsList.get(position);

        holder.sms.setText(sms.getSmsBody());
        holder.mobile.setText(sms.getSmsNumber());
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }
}
