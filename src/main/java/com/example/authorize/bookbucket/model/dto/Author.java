package com.example.authorize.bookbucket.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Author {

    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String biography;
    private List<Book> writtenBook;

}
