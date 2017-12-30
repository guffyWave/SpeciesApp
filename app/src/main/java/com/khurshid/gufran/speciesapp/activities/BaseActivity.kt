package com.khurshid.gufran.speciesapp.activities

import android.support.v7.app.AppCompatActivity

import com.khurshid.gufran.speciesapp.management.SpeciesApp

/*
    Code Prepared by **Gufran Khurshid**.
    Sr. Android Developer.
    Email Id : gufran.khurshid@gmail.com
    Skype Id : gufran.khurshid
    Date: **30 December, 2017.**

    Description: **BaseActivity is used to initialize common variables and call common methods**

    All Rights Reserved.
*/

open abstract class BaseActivity : AppCompatActivity() {
    var TAG = SpeciesApp.TAG + "_BaseActivity"
}
