package com.fedorovigor.bookstore.bookbucket.exception;

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
