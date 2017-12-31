package com.khurshid.gufran.speciesapp.presenter;

import com.khurshid.gufran.speciesapp.communication.retrofit.response.ServerResponse;
import com.khurshid.gufran.speciesapp.dao.SpeciesDao;
import com.khurshid.gufran.speciesapp.dao.SpeciesDaoImpl;
import com.khurshid.gufran.speciesapp.entity.Specie;
import com.khurshid.gufran.speciesapp.exceptions.SpecieAppException;
import com.khurshid.gufran.speciesapp.view.SpecieView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/*
    Code Prepared by **Gufran Khurshid**.
    Sr. Android Developer.
    Email Id : gufran.khurshid@gmail.com
    Skype Id : gufran.khurshid
    Date: **30 December, 2017.**

    Description: **Presenter that bind the data from server with View**

    All Rights Reserved.
*/
public class SpeciesPresenter {
    private final SpeciesDao mDao;
    private final SpecieView mView;
    private CompositeSubscription mSubscriptions;


    public SpeciesPresenter(SpeciesDao mDao, SpecieView mView) {
        this.mDao = mDao;
        this.mView = mView;
        this.mSubscriptions = new CompositeSubscription();
    }

    public void getSpeciesList(String page) {
        mView.showWait();
        Subscription subscription = mDao.getSpeciesList(page, new SpeciesDaoImpl.GetSpeciesListCallback() {
            @Override
            public void onSuccess(ServerResponse serverResponse) {
                mView.removeWait();
                mView.getSpeciesList(serverResponse);
            }

            @Override
            public void onError(SpecieAppException exception) {
                mView.removeWait();
                mView.onFailure(exception.getMessage());
            }
        });
        mSubscriptions.add(subscription);
    }

    public void onStop() {
        mSubscriptions.unsubscribe();
    }


    public synchronized void changeSpecieState(Specie specie) {
        if (specie.getSpecieStatus().equals(Specie.ACTIVE)) {
            specie.setSpecieStatus(Specie.EXTINCT);
        } else {
            specie.setSpecieStatus(Specie.ACTIVE);
        }
    }
}
