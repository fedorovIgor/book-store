package com.fedorovigor.bookstore.bookbucket.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AuthorRequest {

    private Integer authorId;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String biography;
    private List<TitleRequest> writtenBooks;

}
