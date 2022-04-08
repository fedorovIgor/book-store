package com.fedorovigor.bookstore.bookbucket.exception;

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
