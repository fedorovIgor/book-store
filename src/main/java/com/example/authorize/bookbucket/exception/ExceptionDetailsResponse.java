package com.example.authorize.bookbucket.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ExceptionDetailsResponse{

    private final String message;
    private final HttpStatus status;
    private final LocalDateTime timestamp;

}
