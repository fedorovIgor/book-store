package com.example.authorize.bookbucket.service;

import com.example.authorize.bookbucket.model.dto.*;

import java.util.List;

public interface BookBucketReadService {

    Book getBookById(int id);
    List<Book> getAllBooksInTitle(int titleId);

    AuthorResponse getAuthor(int id);
    PagebleResponse getAllAuthors(int page);

    List<Genre> getAllGenres();

    PagebleResponse getAllTitle(int pageNumber);
    PagebleResponse getTitlesByGenre(String genre, int pageNumber);
    PagebleResponse getTitlesByAuthorId(int authorId, int pageNumber);

}
