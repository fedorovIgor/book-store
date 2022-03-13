package com.example.authorize.bookbucket.controller;

import com.example.authorize.bookbucket.exception.ResourceNotFoundException;
import com.example.authorize.bookbucket.model.dto.AuthorRequest;
import com.example.authorize.bookbucket.model.dto.GenreResponse;
import com.example.authorize.bookbucket.model.dto.PageableResponse;
import com.example.authorize.bookbucket.service.BookBucketCUDService;
import com.example.authorize.bookbucket.service.BookBucketReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bucket")
@RequiredArgsConstructor
public class BookBucketController {

    private final BookBucketReadService bookBucketReadService;
    private final BookBucketCUDService bookBucketCUDService;

    @GetMapping(value = "title/{page}")
    public PageableResponse getAllTitle(@PathVariable("page") int page) {
        return bookBucketReadService.getAllTitle(page);
    }

    @GetMapping(value = "title/{authorId}/{page}")
    public PageableResponse getAllTitleWithAuthor(@PathVariable("page") int page,
                                                  @PathVariable("authorId") int authorId) {
        return bookBucketReadService.getTitlesByAuthorId(authorId, page);
    }

    @GetMapping(value = "title/{genre}/{page}")
    public PageableResponse getAllTitleWithGenre(@PathVariable("page") int page,
                                                 @PathVariable("genre") String genre) {
        return bookBucketReadService.getTitlesByGenre(genre, page);
    }

    @GetMapping(value = "genre")
    public List<GenreResponse> getAllGenre() {
        return bookBucketReadService.getAllGenres();
    }

    @GetMapping(value = "author/{page}")
    public PageableResponse getAuthorPage(@PathVariable("page") int page) {
        return bookBucketReadService.getAllAuthors(page);
    }

    @PostMapping(value = "/create")
    public void createAuthorTitle(@RequestBody AuthorRequest request) {
        bookBucketCUDService.createAuthorTitle(request);
    }

}
