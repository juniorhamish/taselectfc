package com.taselectfc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Fixture already exists.")
public class DuplicateFixtureException extends RuntimeException {

    private static final long serialVersionUID = -7433741526129378611L;

}
