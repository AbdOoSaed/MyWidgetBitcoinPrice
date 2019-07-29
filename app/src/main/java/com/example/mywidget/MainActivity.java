/*
 * Copyright (c) 2019/7/28.
 * Created by AbdOo Saed from Egypt.
 * all Copyright reserved.
 */

package com.example.mywidget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import static com.example.mywidget.MyService.DATA_BROADCAST_BIT;

public class MainActivity extends AppCompatActivity {
    private TextView tvPriceMain, tvLastUpdateMain;
    private BroadcastReceiver broadcastReceiver;
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvPriceMain = findViewById(R.id.tvPriceMain);
        tvLastUpdateMain = findViewById(R.id.tvLastUpdateMain);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        tvPriceMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String[] parts = PreferenceHelper.getDataBit(context).split("\\$");
                tvPriceMain.setText(parts[0] + "$");
                tvLastUpdateMain.setText(parts[1].split("\n")[0] + parts[1].split("\n")[1]);

            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(DATA_BROADCAST_BIT));


    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}
