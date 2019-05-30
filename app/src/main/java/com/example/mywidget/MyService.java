package com.example.mywidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.mywidget.MainActivity.url;

public class MyService extends Service {
    String data = "";
    Intent intent;
    RemoteViews remoteViews;

    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.intent = intent;
        if (ApplcationClass.isOnline(getApplicationContext())) {
            remoteViews = new RemoteViews(getPackageName(), R.layout.new_app_widget);

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        getdata();
                        try {
                            Thread.sleep(1000);
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
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void getdata() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String s = object.getString("USD");
                    JSONObject objects = new JSONObject(s);
                    String ss = objects.getString("buy");
                    data = ss + " $";
                    Log.i("8765", data);
                    remoteViews.setTextViewText(R.id.appwidget_text, data);
                    PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),
                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
                    AppWidgetManager.getInstance(getApplicationContext())
                            .updateAppWidget(intent.getIntExtra("appwidgetid", 0), remoteViews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent intentRestartService = new Intent(getApplicationContext(), this.getClass());
        intentRestartService.setPackage(getPackageName());
        startService(intentRestartService);
        super.onTaskRemoved(rootIntent);

    }
}
