/*
 * Copyright (c) 2019/7/28.
 * Created by AbdOo Saed from Egypt.
 * all Copyright reserved.
 */

package com.example.mywidget;

import android.app.Application;

import static com.example.mywidget.Util.startServiceWidget;

public class ApplcationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startServiceWidget(getApplicationContext());
    }


}
