package com.example.authorize.bookbucket.service;

import com.example.authorize.bookbucket.repository.AuthorRepository;
import com.example.authorize.bookbucket.repository.BookRepository;
import com.example.authorize.bookbucket.repository.GenreRepository;
import com.example.authorize.bookbucket.repository.TitleRepository;
import com.example.authorize.bookbucket.exception.ResourceNotFoundException;
import com.example.authorize.bookbucket.model.dto.*;
import com.example.authorize.bookbucket.model.entity.AuthorEntity;
import com.example.authorize.bookbucket.model.entity.BookEntity;
import com.example.authorize.bookbucket.model.entity.TitleEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookBucketReadServiceImplement implements BookBucketReadService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final TitleRepository titleRepository;
    private final GenreRepository genreRepository;

    @Override
    public Book getBookById(int id) {
        Optional<BookEntity> byId = bookRepository.findById(id);
        var bookEntity = byId.orElseThrow(() -> new ResourceNotFoundException(String.format(
                "Book with id [%s] not exist")));

        return bookEntityToBook(bookEntity);
    }

    @Override
    public List<TitleAuthorGenre> getTitlesByGenre(String genre) {
        String genreUpper = genre.toUpperCase();
        
        if (!genreRepository.isExistsByName(genreUpper))
            throw new ResourceNotFoundException(String.format(
                    "Genre [%s] not exist",genre));

        List<TitleEntity> titleWithGenreEntity = titleRepository.findTitlesInGenre(genreUpper);
        
        if (titleWithGenreEntity.isEmpty())
            throw new ResourceNotFoundException(String.format(
                    "No one book find with genre [%s]",genre));

        var titlesInGenre = titleWithGenreEntity.stream()
                .map(this::titleEntityToTitleAuthorGenre)
                .collect(Collectors.toList());
        
        return titlesInGenre;
    }

    @Override
    public List<TitleAuthorGenre> getTitlesByAuthor(int authorId) {

        if (!authorRepository.existsById(authorId))
            throw new ResourceNotFoundException(String.format(
                    "Author with id [%i] not exist", authorId));

        List<TitleEntity> titlesWithAuthor = titleRepository.findTitlesInAuthor(authorId);

        if(titlesWithAuthor.isEmpty())
            throw new ResourceNotFoundException(String.format(
                    "No one book find with authorId [%i]",authorId));

        var result = titlesWithAuthor.stream()
                .map(this::titleEntityToTitleAuthorGenre)
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public List<Book> getAllBooksInTitle(int titleId) {

        if (!titleRepository.existsById(titleId))
            throw new ResourceNotFoundException(String.format(
                    "Author with id [%i] not exist", titleId));

        Optional<TitleEntity> titleWithBooksOptional = titleRepository.findTitleWithBooks(titleId);

        var titleWithBooks = titleWithBooksOptional.orElseThrow(() ->
            new ResourceNotFoundException(String.format(
                    "No one book find by title id [%i]",titleId)));

        var result = titleWithBooks.getBooksEntity().stream()
                .map(this::bookEntityToBook)
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public Author getAuthor(int id) {
        return null;
    }

    @Override
    public AuthorPageResponse getAllAuthors(int givenPage) {

        int size = 20;

        int innerPage = givenPage - 1;

        Page<AuthorEntity> authorsEntityPage = authorRepository
                .findAll(PageRequest.of(innerPage, size, Sort.by("lastName")));

        List<Author> authors = authorsEntityPage.getContent().stream()
                .map(this::authorEntityToAuthor)
                .collect(Collectors.toList());

        AuthorPageResponse authorPage = new AuthorPageResponse(
                authorsEntityPage.getTotalElements(),
                givenPage,
                authorsEntityPage.getTotalPages(),
                authors
        );

        return authorPage;
    }

    @Override
    public List<Genre> getAllGenres() {
        return null;
    }

    @Override
    public List<TitleAuthorGenre> getAllTitle() {
        return null;
    }


    private TitleAuthorGenre titleEntityToTitleAuthorGenre(TitleEntity entity) {
        var title = new TitleAuthorGenre();

        title.setDateWriting(entity.getDateWriting());
        title.setImageUrl(entity.getImageUrl());
        title.setName(entity.getName());

        if (!entity.getAuthors().isEmpty()) {
            String authors = entity.getAuthors().stream()
                    .map(this::authorEntityToAuthor)
                    .map(s -> s.getFirstName() + " " + s.getLastName())
                    .collect(Collectors.joining(","));

            title.setAuthorsNames(authors);
        }

        if (!entity.getGenres().isEmpty()) {
            var genre = entity.getGenres().stream()
                    .map(g -> g.getGenreName())
                    .collect(Collectors.joining(","));

            title.setGenres(genre);
        }

        return title;
    }

    private Author authorEntityToAuthor(AuthorEntity entity) {
        Author author = new Author();

        author.setFirstName(entity.getFirstName());
        author.setLastName(entity.getLastName());
        author.setBirthday(entity.getBirthday());
        author.setBiography(entity.getBiography());

        return author;
    }

    private Book bookEntityToBook(BookEntity entity) {
        var book = new Book();
        book.setPublisher(entity.getPublisher());
        book.setDatePublication(entity.getDatePublication());
        book.setPrice(entity.getPrice());

        return book;
    }
}
