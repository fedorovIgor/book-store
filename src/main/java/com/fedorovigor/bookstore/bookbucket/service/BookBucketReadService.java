package com.fedorovigor.bookstore.bookbucket.service;

import com.fedorovigor.bookstore.bookbucket.model.dto.AuthorResponse;
import com.fedorovigor.bookstore.bookbucket.model.dto.Book;
import com.fedorovigor.bookstore.bookbucket.model.dto.GenreResponse;
import com.fedorovigor.bookstore.bookbucket.model.dto.PageableResponse;

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
