package com.khurshid.gufran.speciesapp.communication.retrofit;


import com.khurshid.gufran.speciesapp.communication.retrofit.response.ServerResponse;
import com.khurshid.gufran.speciesapp.management.KeyIds;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * It contains all the APIs mapped to respective method
 *
 * @author Gufran Khurshid
 * @version 1.0
 * @since 31/12/17
 */

public interface SpeciesAppAPI {
    @GET(KeyIds.GET_SPECIES)
    Observable<ServerResponse> getSpecies(@Query("page") String page);
}
