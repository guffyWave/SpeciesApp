package com.khurshid.gufran.speciesapp.management;

import android.app.Application;
import android.content.Context;

/*
    Code Prepared by **Gufran Khurshid**.
    Sr Android Developer.
    Email Id : gufran.khurshid@gmail.com
    Skype Id : gufran.khurshid
    Date: **30 December, 2017.**

    Description: **Application class**

    All Rights Reserved.
*/
public class SpeciesApp extends Application {

    private static SpeciesApp speciesApp;
    public static String TAG = "SPECIES APP";

    @Override
    public void onCreate() {
        super.onCreate();
        speciesApp = SpeciesApp.this;
    }

    public SpeciesApp getInstance() {
        return speciesApp;
    }

    public static Context getAppContext() {
        return speciesApp.getApplicationContext();
    }
}
