package com.fedorovigor.bookstore.bookbucket.controller;

import com.fedorovigor.bookstore.bookbucket.service.AuthorService;
import com.fedorovigor.bookstore.bookbucket.service.BookBucketReadService;
import com.fedorovigor.bookstore.bookbucket.service.GenreService;
import com.fedorovigor.bookstore.bookbucket.service.TitleService;
import com.fedorovigor.bookstore.bookbucket.model.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bucket")
@RequiredArgsConstructor
public class BookBucketController {

    private final BookBucketReadService bookBucketReadService;

    private final AuthorService authorService;
    private final TitleService titleService;
    private final GenreService genreService;

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


    @PostMapping(value = "author")
    public void createAuthor(@RequestBody AuthorRequest request) {
        authorService.createAuthor(request);
    }

    @PostMapping(value = "title")
    public void createTitle(@RequestBody TitleRequest request) {
        titleService.createTitle(request);
    }

    @PostMapping(value = "genre")
    public void createGenre(@RequestBody GenreRequest request) {
        genreService.createGenre(request);
    }


    @PutMapping(value = "title/{id}/book")
    public void addBookToTitle(@PathVariable("id") Integer id,
                               @RequestBody BookRequest request) {
        titleService.addBookToTitle(id, request);
    }

    @PutMapping(value = "author/{id}")
    public void updateAuthor(@RequestBody AuthorRequest request,
                             @PathVariable("id") Integer id) {
        authorService.updateAuthor(id, request);
    }

    @PutMapping(value = "author/{authorId}/title/{titleId}")
    public void addTitlesToAuthor(@PathVariable("titleId") Integer titleId,
                                  @PathVariable("authorId") Integer authorId) {
        authorService.addTitleToAuthor(authorId, titleId);
    }

    @PutMapping(value = "genre/{genreId}/title/{titleId}")
    public void addTitlesToGenre(@PathVariable("genreId") Integer genreId,
                                 @PathVariable("titleId") Integer titleId) {
        genreService.addTitleToGenre(genreId, titleId);
    }


}
