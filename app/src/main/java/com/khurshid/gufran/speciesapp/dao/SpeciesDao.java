package com.khurshid.gufran.speciesapp.dao;

import rx.Subscription;

/*
    Code Prepared by **Gufran Khurshid**.
    Sr. Android Developer.
    Email Id : gufran.khurshid@gmail.com
    Skype Id : gufran.khurshid
    Date: **30 December, 2017.**

    Description: **Dao declares operations on species.
      This can be implemented by others too that wants fetch data form server,other server communication library, database, disk cache etc
     **

    All Rights Reserved.
*/
public interface SpeciesDao {
    Subscription getSpeciesList(final String page, final SpeciesDaoImpl.GetSpeciesListCallback callback);
}
