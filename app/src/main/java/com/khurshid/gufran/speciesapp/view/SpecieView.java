package com.khurshid.gufran.speciesapp.view;

import com.khurshid.gufran.speciesapp.communication.retrofit.response.ServerResponse;
import com.khurshid.gufran.speciesapp.entity.Specie;

/*
    Code Prepared by **Gufran Khurshid**.
    Sr. Android Developer.
    Email Id : gufran.khurshid@gmail.com
    Skype Id : gufran.khurshid
    Date: **30 December, 2017.**

    Description: **View that will declare viewable operation**

    All Rights Reserved.
*/

public interface SpecieView {

    void showWait();

    void removeWait();

    void onFailure(String failureMessage);

    void getSpeciesList(ServerResponse serverResponse);

    void toggleSpecieState(Specie specie);

}
