package com.example.authorize.bookbucket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class IllegalTitleRequestException extends IllegalArgumentException{
    public IllegalTitleRequestException() {
        super();
    }

    public IllegalTitleRequestException(String s) {
        super(s);
    }

    public IllegalTitleRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
