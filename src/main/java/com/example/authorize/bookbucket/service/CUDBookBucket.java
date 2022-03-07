package com.example.authorize.bookbucket.service;

import com.example.authorize.bookbucket.model.dto.Author;
import com.example.authorize.bookbucket.model.dto.Book;
import com.example.authorize.bookbucket.model.dto.Title;
import com.example.authorize.bookbucket.model.dto.TitleAuthorGenre;

public interface CUDBookBucket extends ChangeTitle{

    void createAuthor(Author author);
    void updateAuthor(int id, Author author);
    void deleteAuthor(int id);

    void addGenre(String genre);

    void updateBook(int bookId, Book book);
}
