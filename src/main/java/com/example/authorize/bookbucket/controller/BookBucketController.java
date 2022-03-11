package com.example.authorize.bookbucket.controller;

import com.example.authorize.bookbucket.model.dto.Genre;
import com.example.authorize.bookbucket.model.dto.PagebleResponse;
import com.example.authorize.bookbucket.service.BookBucketReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bucket")
@RequiredArgsConstructor
public class BookBucketController {

    private final BookBucketReadService bookBucketReadService;

    @GetMapping(value = "title/{page}")
    public PagebleResponse getAllTitle(@PathVariable("page") int page) {
        return bookBucketReadService.getAllTitle(page);
    }

    @GetMapping(value = "genre")
    public List<Genre> getAllGenre() {
        return bookBucketReadService.getAllGenres();
    }

    @GetMapping(value = "author/{page}")
    public PagebleResponse getAuthorPage(@PathVariable("page") int page) {
        return bookBucketReadService.getAllAuthors(page);
    }
}
