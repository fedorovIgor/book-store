package com.example.authorize.bookbucket.model.dto;

import lombok.Data;

@Data
public class AuthorResponse implements ResponseData {
    private String firstName;
    private String lastName;
    private String writtenBooks;
}
