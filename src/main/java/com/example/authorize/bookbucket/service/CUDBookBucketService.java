package com.example.authorize.bookbucket.service;

import com.example.authorize.bookbucket.repository.AuthorRepository;
import com.example.authorize.bookbucket.repository.BookRepository;
import com.example.authorize.bookbucket.repository.GenreRepository;
import com.example.authorize.bookbucket.repository.TitleRepository;
import com.example.authorize.bookbucket.exception.ResourceAlreadyExistException;
import com.example.authorize.bookbucket.model.dto.*;
import com.example.authorize.bookbucket.model.entity.AuthorEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CUDBookBucketService implements CUDBookBucket{

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final TitleRepository titleRepository;
    private final GenreRepository genreRepository;


    @Override
    public void createAuthor(Author author) {
        if (authorRepository.isExist(author.getFirstName(),
                author.getLastName(),
                author.getBirthday()))
            throw new ResourceAlreadyExistException(String.format(
                    "Author [%s] already exist", author
            ));

        authorRepository.save(authorToEntity(author));
    }

    @Override
    public void updateAuthor(int id, Author author) {

    }

    @Override
    public void deleteAuthor(int id) {

    }

    @Override
    public void addGenre(String genre) {

    }

    @Override
    public void updateBook(int bookId, Book book) {

    }

    @Override
    public void createTitle(Title title) {

    }

    @Override
    public void createTitle(TitleAuthorGenreRequest fullTitle) {

    }

    @Override
    public void updateTitle(int titleId, Title title) {

    }

    @Override
    public void updateTitle(int titleId, TitleAuthorGenreRequest fullTitle) {

    }

    @Override
    public void addBooksToTitle(int titleId, Book... books) {

    }

    @Override
    public void addBooksToTitle(int titleId, int... booksId) {

    }

    @Override
    public void deleteTitle(int id) {

    }

    private AuthorEntity authorToEntity(Author a) {
        AuthorEntity e = new AuthorEntity();

        e.setBiography(a.getBiography());
        e.setBirthday(a.getBirthday());
        e.setLastName(a.getLastName());
        e.setFirstName(a.getFirstName());

        return e;
    }
}
