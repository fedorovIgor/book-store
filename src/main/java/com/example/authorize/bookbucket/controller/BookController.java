package com.example.authorize.bookbucket.controller;

import com.example.authorize.bookbucket.model.dto.AuthorPageResponse;
import com.example.authorize.bookbucket.model.dto.Genre;
import com.example.authorize.bookbucket.model.dto.TitleAuthorGenre;
import com.example.authorize.bookbucket.service.BookBucketReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bucket")
@RequiredArgsConstructor
public class BookController {

    private final BookBucketReadService bookBucketReadService;

    @GetMapping(value = "book")
    public List<TitleAuthorGenre> getAllTitle() {
        return bookBucketReadService.getAllTitle();
    }

    @GetMapping(value = "genre")
    public List<Genre> getAllGenre() {
        return bookBucketReadService.getAllGenres();
    }

    @GetMapping(value = "author/{page}")
    public AuthorPageResponse getAuthorPage(@PathVariable("page") int page) {
        return bookBucketReadService.getAllAuthors(page);
    }
}
