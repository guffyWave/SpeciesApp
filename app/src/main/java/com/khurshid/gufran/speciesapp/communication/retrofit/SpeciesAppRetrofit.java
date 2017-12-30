package com.khurshid.gufran.speciesapp.communication.retrofit;

import android.content.Context;

import com.khurshid.gufran.speciesapp.R;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * SpeciesAppRetrofit acts main interaction point to access the  SpeciesApp APIs
 * defined in SpeciesAppAPI
 *
 * @author Gufran Khurshid
 * @version 1.0
 * @since 3/16/17
 */

public class SpeciesAppRetrofit {
    private static final int MAX_REQUESTS = 4;
    private static final int CONNECTION_TIMEOUT = 60;// time in seconds
    private static final int READ_TIMEOUT = 60;// time in seconds
    private static final int WRITE_TIMEOUT = 60;// time in seconds
    private static SpeciesAppRetrofit speciesAppRetrofit;
    private static Retrofit retrofit;
    private static String BASE_URL = "";

    private SpeciesAppRetrofit() {
    }

    public static synchronized SpeciesAppAPI getAPI(Context context) {
        if (speciesAppRetrofit == null) {
            speciesAppRetrofit = new SpeciesAppRetrofit();
            BASE_URL = context.getString(R.string.server_uri);

            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(MAX_REQUESTS);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .dispatcher(dispatcher)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    // converter sequence does matter , a lot !
                    .addConverterFactory(GsonConverterFactory.create())  //for request to json conversion
                    .addConverterFactory(ScalarsConverterFactory.create())//for response to string conversion
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        }
        return retrofit.create(SpeciesAppAPI.class);
    }

    public static synchronized Retrofit getRetrofit() {
        return retrofit;
    }


}
