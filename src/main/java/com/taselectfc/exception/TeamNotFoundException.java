package com.taselectfc.exception;

public class TeamNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3097124291512611328L;

    public TeamNotFoundException(String message) {
        super(message);
    }

}
