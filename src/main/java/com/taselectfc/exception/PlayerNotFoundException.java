package com.taselectfc.exception;

public class PlayerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 230845827237635335L;

    public PlayerNotFoundException(String message) {
        super(message);
    }

}
