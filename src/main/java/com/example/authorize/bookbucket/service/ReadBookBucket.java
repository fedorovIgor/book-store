package com.example.authorize.bookbucket.service;

import com.example.authorize.bookbucket.model.dto.*;

import java.util.List;

public interface ReadBookBucket {

    Book getBookById(int id);
    List<Book> getAllBooksInTitle(int titleId);

    Author getAuthor(int id);
    List<Author> getAllAuthors();

    List<Genre> getAllGenres();

    List<TitleAuthorGenre> getAllTitle();
    List<TitleAuthorGenre> getTitlesByGenre(String genre);
    List<TitleAuthorGenre> getTitlesByAuthor(int authorId);

}
