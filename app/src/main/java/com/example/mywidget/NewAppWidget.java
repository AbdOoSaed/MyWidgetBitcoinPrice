/*
 * Copyright (c) 2019/7/28.
 * Created by AbdOo Saed from Egypt.
 * all Copyright reserved.
 */

package com.example.mywidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.Toast;
import static com.example.mywidget.PreferenceHelper.removeAllInt;
import static com.example.mywidget.PreferenceHelper.setIntMaxPrefs;
import static com.example.mywidget.PreferenceHelper.setIntPrefs;
import static com.example.mywidget.Util.startServiceWidget;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        removeAllInt(context);
        int i = 0;
        for (int appWidgetId : appWidgetIds) {
            i++;
            setIntPrefs(i + "", appWidgetId, context);
            setIntMaxPrefs(i, context);
        }
        startServiceWidget(context.getApplicationContext());
    }

    @Override
    public void onEnabled(Context context) {
        startServiceWidget(context.getApplicationContext());
        Toast.makeText(context, "Welcome To Bitcoin Price Widget :)", Toast.LENGTH_SHORT).show();
        // Enter relevant functionality for when the first widget is created


    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context, "i will miss u ;( \n Bitcoin Price Widget", Toast.LENGTH_LONG).show();
        removeAllInt(context);
        // Enter relevant functionality for when the last widget is disabled
    }
}

