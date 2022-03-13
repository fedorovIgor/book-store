package com.example.authorize.bookbucket.service;

import com.example.authorize.bookbucket.model.dto.Genre;
import com.example.authorize.bookbucket.model.dto.TitleRequest;
import com.example.authorize.bookbucket.repository.AuthorRepository;
import com.example.authorize.bookbucket.repository.BookRepository;
import com.example.authorize.bookbucket.repository.GenreRepository;
import com.example.authorize.bookbucket.repository.TitleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class SaveTitlesWithGenreTest {


    @Mock
    private  AuthorRepository authorRepository;
    @Mock
    private  BookRepository bookRepository;
    @Mock
    private  TitleRepository titleRepository;
    @Mock
    private  GenreRepository genreRepository;

    BookBucketCUDServiceImplement underTest;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new BookBucketCUDServiceImplement(authorRepository,
                bookRepository,
                titleRepository,
                genreRepository);
    }

    @Test
    void itShouldSaveTitleWithGenre() {

        List<TitleRequest> givenRequest = List.of(
            new TitleRequest(null,"t1", LocalDate.now(), Set.of(
                    new Genre("g1"),
                    new Genre("g2")
            ), null),
            new TitleRequest(null,"t2", LocalDate.now(), Set.of(
                    new Genre("g1"),
                    new Genre("g3")
            ), null),
            new TitleRequest(null,"t3", null, Set.of(
                    new Genre("g3"),
                    new Genre("g2")
            ),null)
        );

    }
}
