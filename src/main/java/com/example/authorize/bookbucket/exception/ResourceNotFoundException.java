package com.example.authorize.bookbucket.exception;

public class ResourceNotFoundException extends IllegalArgumentException{

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String s) {
        super(s);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
