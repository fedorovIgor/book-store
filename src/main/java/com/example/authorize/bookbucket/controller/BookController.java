package com.example.authorize.bookbucket.controller;

import com.example.authorize.bookbucket.model.dto.Title;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bookbucket/v1/api")
public class BookController {

//    @GetMapping
//    List<TitleResponse> getTitles(){}
//
//    @GetMapping
//    List<BookResponse> getBooksByTitle(TitleRequest request) {}
//
//    @GetMapping
//    List<BookResponse> getUserBooks(Authority authority) {
//
//    }

//    @PostMapping("title")
//    void addNewTitle(@RequestBody Title title) {
//        service.createTitle(title);
//    }

    @PostMapping
    void addNewBook(){}

    @PutMapping
    void changeTitle() {}

}
