package com.taselectfc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such fixture.")
public class FixtureNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1191752750442847511L;

    public FixtureNotFoundException(String message) {
        super(message);
    }

}
