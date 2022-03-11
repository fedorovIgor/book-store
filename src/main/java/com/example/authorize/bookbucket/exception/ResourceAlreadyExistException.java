package com.example.authorize.bookbucket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ResourceAlreadyExistException extends IllegalArgumentException {

    public ResourceAlreadyExistException() {
        super();
    }

    public ResourceAlreadyExistException(String s) {
        super(s);
    }

    public ResourceAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
