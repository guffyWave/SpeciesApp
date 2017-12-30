package com.khurshid.gufran.speciesapp.presenter;

import com.khurshid.gufran.speciesapp.communication.retrofit.response.ServerResponse;
import com.khurshid.gufran.speciesapp.dao.SpeciesDao;
import com.khurshid.gufran.speciesapp.dao.SpeciesDaoImpl;
import com.khurshid.gufran.speciesapp.exceptions.SpecieAppException;
import com.khurshid.gufran.speciesapp.view.SpecieView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by gufran on 30/12/17.
 */

public class SpeciesPresenter {
    private final SpeciesDao dao;
    private final SpecieView view;
    private CompositeSubscription subscriptions;


    public SpeciesPresenter(SpeciesDao dao, SpecieView view) {
        this.dao = dao;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getSpeciesList(String page) {
        view.showWait();
        Subscription subscription = dao.getSpeciesList(page, new SpeciesDaoImpl.GetSpeciesListCallback() {
            @Override
            public void onSuccess(ServerResponse serverResponse) {
                view.removeWait();
                view.getSpeciesList(serverResponse);
            }

            @Override
            public void onError(SpecieAppException exception) {
                view.removeWait();
                view.onFailure(exception.getMessage());
            }
        });
        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
