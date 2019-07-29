/*
 * Copyright (c) 2019/7/28.
 * Created by AbdOo Saed from Egypt.
 * all Copyright reserved.
 */

package com.example.mywidget;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import static com.example.mywidget.PreferenceHelper.getIntMaxPrefs;
import static com.example.mywidget.PreferenceHelper.getIntPrefs;

class Util {
    static String url = "https://blockchain.info/ticker";

    static void startServiceWidget(Context context) {
        if (getIntMaxPrefs(context) != -1) {
            for (int a = 0; a <= 1; a++) {
                Intent inte = new Intent(context, MyService.class);

                inte.putExtra("appwidgetid", getIntPrefs(a + "", context));
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        context.stopService(inte);
                        context.startForegroundService(inte);
                        context.startService(inte);
                    } else {
//                        context.stopService(inte);
                        context.startService(inte);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
