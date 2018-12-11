package com.example.dell.growup.data;

import android.content.SharedPreferences;

import com.example.dell.growupbase.base.GrowUpApplication;

/**
 * Created by dell on 2017/10/9.
 */

public class ToolPerference {

    private static final String ISBACKENABLE = "isback_enable";


    public static void storeIsBackEnable(boolean is){
        SharedPreferences preferences = GrowUpApplication.getInstance().getSharedPreferences(ISBACKENABLE,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Is",is);
        editor.apply();
    }
    public static Boolean getIsBackEnable(){
        SharedPreferences preferences = GrowUpApplication.getInstance().getSharedPreferences(ISBACKENABLE,0);
        return preferences.getBoolean("Is",false);
    }
}
