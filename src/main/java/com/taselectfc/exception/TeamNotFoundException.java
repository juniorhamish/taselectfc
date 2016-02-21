package com.taselectfc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such team.")
public class TeamNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3097124291512611328L;

    public TeamNotFoundException(String message) {
        super(message);
    }

}
