package com.fedorovigor.bookstore.userbucket.controller;

import com.fedorovigor.bookstore.userbucket.model.dto.BookIdRequest;
import com.fedorovigor.bookstore.userbucket.model.dto.UserBookResponse;
import com.fedorovigor.bookstore.userbucket.service.UserBucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/my-store/")
@RequiredArgsConstructor
public class UserBookController {

    private final UserBucketService userBucketService;

    @GetMapping(value = "book")
    public UserBookResponse getUserBooks(Authentication authentication) {
       return userBucketService.getUserBooks(authentication.getName());
    }

    @PostMapping(value = "book")
    public void addBookToUser(
            @RequestBody BookIdRequest bookId,
            Authentication authentication) {
        userBucketService.addBookToUser(authentication.getName(), bookId);
    }
}
