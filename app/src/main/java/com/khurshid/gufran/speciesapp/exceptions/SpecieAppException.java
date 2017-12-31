package com.khurshid.gufran.speciesapp.exceptions;

/*
    Code Prepared by **Gufran Khurshid**.
    Sr. Android Developer.
    Email Id : gufran.khurshid@gmail.com
    Skype Id : gufran.khurshid
    Date: **31 December, 2017.**

    Description: **Represents and encapsulate all the exceptions
    Crashlytics can be used to trace exceptions in different types of devices
    **


    All Rights Reserved.
*/


public class SpecieAppException extends Exception {

    public SpecieAppException(String message) {
        super(message);
    }


    public SpecieAppException(String message, Throwable e) {
        super(message, e);
    }
}
