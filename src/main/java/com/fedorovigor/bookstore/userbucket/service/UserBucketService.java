package com.fedorovigor.bookstore.userbucket.service;

import com.fedorovigor.bookstore.userbucket.model.dto.BookIdRequest;
import com.fedorovigor.bookstore.userbucket.model.dto.UserBookResponse;

public interface UserBucketService {

    void addBookToUser(String name, BookIdRequest bookId);
    UserBookResponse getUserBooks(String username);
}
