package com.fedorovigor.bookstore.bookbucket.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRequest {

    private LocalDate datePublication;
    private String publisher;
    private Integer price;

}
