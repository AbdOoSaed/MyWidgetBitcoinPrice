/*
 * Copyright (c) 2019/7/28.
 * Created by AbdOo Saed from Egypt.
 * all Copyright reserved.
 */

package com.example.mywidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.mywidget.PreferenceHelper.removeBitData;
import static com.example.mywidget.PreferenceHelper.storeBitData;
import static com.example.mywidget.Util.isOnline;
import static com.example.mywidget.Util.url;


public class MyService extends Service {
    private String data = "";
    private Intent intent;
    private RemoteViews remoteViews;
    public static final String DATA_BROADCAST_BIT = "databit";
    private SimpleDateFormat formatter;
    private String strDate;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        restartService();
    }

    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.intent = intent;
        if (isOnline(getApplicationContext())) {
            remoteViews = new RemoteViews(getPackageName(), R.layout.new_app_widget);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        getData();
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            t.start();

        } else {
            stopSelf();
        }
//        return super.onStartCommand(intent, flags, startId);
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    data = new JSONObject(new JSONObject(response).getString("USD")).getString("buy") + "$";
                    //send date to main activty
                    getApplicationContext().sendBroadcast(new Intent(DATA_BROADCAST_BIT));
                    formatter = new SimpleDateFormat("M/dd \n hh:mm a", new Locale("EN"));
                     strDate = formatter.format(new Date());
                    removeBitData(getApplicationContext());
                    storeBitData(data+strDate, getApplicationContext());
                    remoteViews.setTextViewText(R.id.appwidget_text, data);
                    remoteViews.setTextViewText(R.id.app_widget_time_text, strDate);
                    PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),
                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    remoteViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
//                    Toast.makeText(getApplicationContext(), "Bitcoin Price Widget Refreshed $$", Toast.LENGTH_SHORT).show();

                    AppWidgetManager.getInstance(getApplicationContext())
                            .updateAppWidget(intent.getIntExtra("appwidgetid", 0), remoteViews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "" + error + "...Ser", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        restartService();
        super.onTaskRemoved(rootIntent);

    }

    private void restartService() {
        Intent intentRestartService = new Intent(getApplicationContext(), this.getClass());
        intentRestartService.setPackage(getPackageName());
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getApplicationContext().startForegroundService(intentRestartService);
                getApplicationContext().startService(intentRestartService);
            } else {
                getApplicationContext().startService(intentRestartService);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
