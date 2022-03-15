package com.example.authorize.bookbucket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class IllegalAuthorRequestException extends IllegalArgumentException{
    public IllegalAuthorRequestException() {
        super();
    }

    public IllegalAuthorRequestException(String s) {
        super(s);
    }

    public IllegalAuthorRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
