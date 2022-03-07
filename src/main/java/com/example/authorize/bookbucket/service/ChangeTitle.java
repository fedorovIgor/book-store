package com.example.authorize.bookbucket.service;

import com.example.authorize.bookbucket.model.dto.Book;
import com.example.authorize.bookbucket.model.dto.Title;
import com.example.authorize.bookbucket.model.dto.TitleAuthorGenre;
import com.example.authorize.bookbucket.model.dto.TitleAuthorGenreRequest;

public interface ChangeTitle {

    void createTitle(Title title);
    void createTitle(TitleAuthorGenreRequest fullTitle);
    void updateTitle(int titleId, Title title);
    void updateTitle(int titleId, TitleAuthorGenreRequest fullTitle);
    void addBooksToTitle(int titleId, Book... books);
    void addBooksToTitle(int titleId, int... booksId);
    void deleteTitle(int id);

}
