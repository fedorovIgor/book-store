package com.fedorovigor.bookstore.bookbucket.model.dto;

import lombok.Data;

@Data
public class AuthorResponse implements ResponsePageable {

    private int id;
    private String firstName;
    private String lastName;
    private String writtenBooks;
}
