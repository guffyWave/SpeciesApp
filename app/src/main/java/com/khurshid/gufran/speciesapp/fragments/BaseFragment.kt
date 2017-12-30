package com.khurshid.gufran.speciesapp.fragments

import android.support.v4.app.Fragment
import android.view.View

import com.khurshid.gufran.speciesapp.management.SpeciesApp

/*
    Code Prepared by **Gufran Khurshid**.
    Sr. Android Developer.
    Email Id : gufran.khurshid@gmail.com
    Skype Id : gufran.khurshid
    Date: **30 December, 2017.**

    Description: **BaseFragment is used to initialize common variables and call common methods**

    All Rights Reserved.
*/


open abstract class BaseFragment : Fragment() {
    var TAG = SpeciesApp.TAG + "_BaseFragment"
    var rootView: View? = null//root view of the fragment
}
