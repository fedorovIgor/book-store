package com.fedorovigor.bookstore.bookbucket.service;

import com.fedorovigor.bookstore.bookbucket.repository.AuthorRepository;
import com.fedorovigor.bookstore.bookbucket.repository.TitleRepository;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class SaveTitlesWithGenreTest {


    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private TitleRepository titleRepository;
    @Mock
    private GenreService genreService;

}
