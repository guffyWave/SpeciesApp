package com.khurshid.gufran.speciesapp.exceptions;

/**
 * Created by gufran on 30/12/17.
 * Represents all the exceptions
 */

public class SpecieAppException extends Exception {

    public SpecieAppException(String message) {
        super(message);
    }


    public SpecieAppException(String message, Throwable e) {
        super(message, e);
    }
}
