package com.example.authorize.bookbucket.service;

import com.example.authorize.bookbucket.model.dto.*;

import java.util.List;

public interface BookBucketReadService {

    Book getBookById(int id);
    List<Book> getAllBooksInTitle(int titleId);

    AuthorResponse getAuthor(int id);
    PageableResponse getAllAuthors(int page);

    List<GenreResponse> getAllGenres();

    PageableResponse getAllTitle(int pageNumber);
    PageableResponse getTitlesByGenre(String genre, int pageNumber);
    PageableResponse getTitlesByAuthorId(int authorId, int pageNumber);

}
