/*
 * Copyright (c) 2019/8/7.
 * Created by AbdOo Saed from Egypt.
 * all Copyright reserved.
 */

package com.example.mywidget;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.mywidget.MyService.DATA_BROADCAST_BIT;


class PreferenceHelper {
    private static final String PREFNAME = "preferencename";

    static void setIntPrefs(String key, int i, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFNAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, i);
        editor.apply();
    }

    static int getIntPrefs(String key, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFNAME, 0);
        return prefs.getInt(key, -1);
    }

    static void setIntMaxPrefs(int i, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFNAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("max");
        editor.putInt("max", i);
        editor.apply();
    }

    static int getIntMaxPrefs(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFNAME, 0);
        return prefs.getInt("max", -1);
    }

    static boolean storeBitData(String datsBit, Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DATA_BROADCAST_BIT, datsBit);
        editor.apply();
        return true;
    }

    static String getDataBit(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DATA_BROADCAST_BIT, null);
    }

    static void removeBitData(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(DATA_BROADCAST_BIT);
        editor.apply();
    }

    static void removeAllInt(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFNAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear().apply();
    }

}