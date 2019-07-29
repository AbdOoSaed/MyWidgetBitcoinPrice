/*
 * Copyright (c) 2019/7/28.
 * Created by AbdOo Saed from Egypt.
 * all Copyright reserved.
 */

package com.example.mywidget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static com.example.mywidget.Util.startServiceWidget;

public class StartActivityOnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            startServiceWidget(context);
        } else {
            startServiceWidget(context);
            Toast.makeText(context, "" + intent.getAction(), Toast.LENGTH_LONG).show();
        }
    }
}
