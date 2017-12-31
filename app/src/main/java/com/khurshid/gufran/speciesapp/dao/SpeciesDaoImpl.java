package com.khurshid.gufran.speciesapp.dao;

import android.content.Context;

import com.khurshid.gufran.speciesapp.communication.retrofit.SpeciesAppRetrofit;
import com.khurshid.gufran.speciesapp.communication.retrofit.response.ServerResponse;
import com.khurshid.gufran.speciesapp.exceptions.SpecieAppException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/*
    Code Prepared by **Gufran Khurshid**.
    Sr. Android Developer.
    Email Id : gufran.khurshid@gmail.com
    Skype Id : gufran.khurshid
    Date: **30 December, 2017.**

    Description: **DaoImpl instantiate the retrofit client
      Rx makes requests on worker thread
      and observes on main thread
     **

    All Rights Reserved.
*/
public class SpeciesDaoImpl implements SpeciesDao {

    Context mContext;

    public SpeciesDaoImpl(Context mContext) {
        this.mContext = mContext;
    }

    public synchronized Subscription getSpeciesList(final String page, final GetSpeciesListCallback callback) {
        return SpeciesAppRetrofit.getAPI(mContext).getSpecies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ServerResponse>>() {
                    @Override
                    public Observable<? extends ServerResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<ServerResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Crashlytics can be used to trace exceptions in different types of devices
                        callback.onError(new SpecieAppException("Some Error occured", e));
                    }

                    @Override
                    public void onNext(ServerResponse ServerResponse) {
                        //the server response can be cached too using retrofit cache or diskLRU cache
                        callback.onSuccess(ServerResponse);
                    }
                });
    }


    public interface GetSpeciesListCallback {
        void onSuccess(ServerResponse serverResponse);

        void onError(SpecieAppException exception);
    }
}
