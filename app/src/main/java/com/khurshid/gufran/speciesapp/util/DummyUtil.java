package com.khurshid.gufran.speciesapp.util;

import com.khurshid.gufran.speciesapp.R;
import com.khurshid.gufran.speciesapp.management.KeyIds;
import com.khurshid.gufran.speciesapp.management.SpeciesApp;

/**
 * Created by gufran on 30/12/17.
 */

public class DummyUtil {

    public static String getNextPageNumber(String nextPageString) {
        nextPageString = nextPageString.replace(SpeciesApp.getAppContext().getString(R.string.server_uri), "");
        nextPageString = nextPageString.replace(KeyIds.GET_SPECIES, "");
        nextPageString = nextPageString.replace("?page=", "");
        return nextPageString;
    }

//    public static void main(String args[]) {
//        String res = getNextPageNumber("https://swapi.co/api/species/?page=3");
//        System.out.println("Res : " + res);
//
//    }
}
