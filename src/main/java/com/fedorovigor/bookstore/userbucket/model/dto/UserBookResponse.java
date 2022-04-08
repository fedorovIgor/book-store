package com.fedorovigor.bookstore.userbucket.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserBookResponse {
    List<BookFullResponse> books;

    public UserBookResponse() {
        books = new ArrayList<>();
    }

    public UserBookResponse(List<BookFullResponse> fullBooks) {
        books = new ArrayList<>();
        books.addAll(fullBooks);
    }
}
