package com.fedorovigor.bookstore.bookbucket.exception;

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
