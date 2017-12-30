package com.khurshid.gufran.speciesapp.dao;

import com.khurshid.gufran.speciesapp.entity.Specie;

import java.util.List;

import rx.Subscription;

/**
 * Created by gufran on 30/12/17.
 */

public interface SpeciesDao {
    Subscription getSpeciesList(final String page, final SpeciesDaoImpl.GetSpeciesListCallback callback);
}
