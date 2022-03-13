package com.example.authorize.bookbucket.model.dto;

import lombok.Data;

@Data
public class AuthorResponse implements ResponsePageable {
    private String firstName;
    private String lastName;
    private String writtenBooks;
}
