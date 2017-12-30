package com.khurshid.gufran.speciesapp.dao;

import android.content.Context;

import com.khurshid.gufran.speciesapp.communication.retrofit.SpeciesAppAPI;
import com.khurshid.gufran.speciesapp.communication.retrofit.SpeciesAppRetrofit;
import com.khurshid.gufran.speciesapp.communication.retrofit.response.ServerResponse;
import com.khurshid.gufran.speciesapp.exceptions.SpecieAppException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by gufran on 30/12/17.
 */

public class SpeciesDaoImpl implements SpeciesDao{

    Context context;

    public SpeciesDaoImpl(Context context) {
        this.context = context;
    }

    public Subscription getSpeciesList(final String page, final GetSpeciesListCallback callback) {
        return SpeciesAppRetrofit.getAPI(context).getSpecies(page)
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
                        callback.onError(new SpecieAppException("Some Error occured", e));
                    }

                    @Override
                    public void onNext(ServerResponse ServerResponse) {
                        callback.onSuccess(ServerResponse);
                    }
                });
    }


    public interface GetSpeciesListCallback {
        void onSuccess(ServerResponse serverResponse);

        void onError(SpecieAppException exception);
    }
}
