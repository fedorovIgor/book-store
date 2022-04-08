package com.fedorovigor.bookstore.bookbucket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler( {
            IllegalAuthorRequestException.class,
            IllegalTitleRequestException.class,
            ResourceAlreadyExistException.class
    })
    public final ResponseEntity handleIllegalArgumentException(RuntimeException exception,
                                                            WebRequest request) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;

        var response = new ExceptionDetailsResponse(
                exception.getMessage(),
                status,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, status );
    }


    @ExceptionHandler( {
            ResourceNotFoundException.class
    })
    public final ResponseEntity<Object> handleResourceNotFoundException(RuntimeException exception,
                                                            WebRequest request) {

        var status = HttpStatus.NOT_FOUND;

        var response = new ExceptionDetailsResponse(
                exception.getMessage(),
                status,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, status );
    }

}
