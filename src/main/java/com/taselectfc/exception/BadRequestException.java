package com.taselectfc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = -5646457285675541123L;

    public BadRequestException(String message) {
        super(message);
    }

}
