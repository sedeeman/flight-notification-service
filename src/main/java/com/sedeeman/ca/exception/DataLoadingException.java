package com.sedeeman.ca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataLoadingException extends RuntimeException {
    public DataLoadingException(String message) {
        super(message);
    }
}
