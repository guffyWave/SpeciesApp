package com.khurshid.gufran.speciesapp.view;

import com.khurshid.gufran.speciesapp.communication.retrofit.response.ServerResponse;

/**
 * Created by gufran on 30/12/17.
 */

public interface SpecieView {

    void showWait();

    void removeWait();

    void onFailure(String failureMessage);

    void getSpeciesList(ServerResponse serverResponse);

}
