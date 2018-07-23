package com.example.anant.makeyourappmaterial;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by anant on 20/7/18.
 */

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
