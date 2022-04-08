package com.fedorovigor.bookstore.bookbucket.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ExceptionDetailsResponse{

    private final String message;
    private final HttpStatus status;
    private final LocalDateTime timestamp;

}
