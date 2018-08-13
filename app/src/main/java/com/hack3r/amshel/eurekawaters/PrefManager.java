package com.hack3r.amshel.eurekawaters;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    int mode = 0;
    private final String prefName = "eureka_welcome_screen";
    private final String is_first_time_launch = "isFirstTime";

    public PrefManager(Context c){
        this.context = c;
        sharedPreferences = context.getSharedPreferences(prefName, mode);
        editor = sharedPreferences.edit();
    }

    public void setFirstTimeLaunch(Boolean isFirstTime){
        editor.putBoolean(is_first_time_launch, isFirstTime);
        editor.commit();
    }

    public Boolean isFirstTimeLaunch(){
        return sharedPreferences.getBoolean(is_first_time_launch, true);
    }
}
