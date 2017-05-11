package com.example.wkmin.showmarker;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by wkmin on 2017. 5. 11..
 *
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);
    }
}
