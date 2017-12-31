package com.khurshid.gufran.speciesapp.util;

import com.khurshid.gufran.speciesapp.R;
import com.khurshid.gufran.speciesapp.management.KeyIds;
import com.khurshid.gufran.speciesapp.management.SpeciesApp;
/*
    Code Prepared by **Gufran Khurshid**.
    Sr. Android Developer.
    Email Id : gufran.khurshid@gmail.com
    Skype Id : gufran.khurshid
    Date: **31 December, 2017.**

    Description: **Converter utility**

    All Rights Reserved.
*/


public class ConverterUtil {

    /*Extracts the pageNumber from a whole URL string */
    public static String getNextPageNumber(String nextPageString) {
        nextPageString = nextPageString.replace(SpeciesApp.getAppContext().getString(R.string.server_uri), "");
        nextPageString = nextPageString.replace(KeyIds.GET_SPECIES, "");
        nextPageString = nextPageString.replace("?page=", "");
        return nextPageString;
    }

}
