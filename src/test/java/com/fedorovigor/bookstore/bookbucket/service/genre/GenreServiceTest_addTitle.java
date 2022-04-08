package com.fedorovigor.bookstore.bookbucket.service.genre;

import com.fedorovigor.bookstore.bookbucket.exception.ResourceNotFoundException;
import com.fedorovigor.bookstore.bookbucket.model.entity.GenreEntity;
import com.fedorovigor.bookstore.bookbucket.model.entity.TitleEntity;
import com.fedorovigor.bookstore.bookbucket.repository.GenreRepository;
import com.fedorovigor.bookstore.bookbucket.repository.TitleRepository;
import com.fedorovigor.bookstore.bookbucket.service.BucketMapping;
import com.fedorovigor.bookstore.bookbucket.service.GenreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest_addTitle {
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private TitleRepository titleRepository;
    @Mock
    private BucketMapping mapping;
    @Captor
    ArgumentCaptor<GenreEntity> genreCaptor;

    private GenreService mocked;


    private Integer genreId;
    private Integer titleId;
    private GenreEntity genreEntity;
    private TitleEntity titleEntity;

    @BeforeEach
    public  void init() {
        mocked = new GenreService(
                genreRepository,
                titleRepository,
                mapping
        );

        initEntities();
    }

    private void initEntities() {

        genreId = 1;
        titleId = 10;

        String genreName = "GENRE";

        genreEntity = new GenreEntity();
        genreEntity.setId(genreId);
        genreEntity.setGenreName(genreName);
        genreEntity.setTitles(new ArrayList<>());

        titleEntity = new TitleEntity();
        titleEntity.setId(titleId);
    }


    @Test
    public void shouldAddTitleToGenre() {
//        1. Mock titleRepository.findById
//          1.1 if empty -> throw ResourceNotFoundException(String.format(
//                        "Title with [%s] id not found", titleId
//                )
//        2. Mock genreRepository.findById
//          2.1 if empty -> throw ResourceNotFoundException(String.format(
//                        "Author with [%s] id not found", genreId
//                )
//        3. Mock genreRepository.save with added Title

        Mockito.when(titleRepository.findById(titleId))
                .thenReturn(Optional.of(titleEntity));

        Mockito.when(genreRepository.findById(genreId))
                .thenReturn(Optional.of(genreEntity));

//        when
        mocked.addTitleToGenre(genreId, titleId);

        Mockito.verify(genreRepository)
                .save(genreCaptor.capture());

        Assertions.assertEquals(true,
                genreCaptor.getValue().getTitles().contains(titleEntity));
    }

    @Test
    public void shouldThrow_whenTitleNotExist() {

        Mockito.when(titleRepository.findById(titleId))
                .thenReturn(Optional.empty());


//        when
        var ex = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> mocked.addTitleToGenre(genreId, titleId));

        genreEntity.addTitleEntity(titleEntity);

//        then
        Mockito.verify(genreRepository, Mockito.times(0))
                .save(genreEntity);

        Assertions.assertEquals(
                (String.format("Title with [%s] id not found", titleId)),
                ex.getMessage());
    }

    @Test
    public void shouldThrow_whenGenreNotExist() {

        Mockito.when(titleRepository.findById(titleId))
                .thenReturn(Optional.of(titleEntity));

        Mockito.when(genreRepository.findById(genreId))
                .thenReturn(Optional.empty());

//        when
        var ex = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> mocked.addTitleToGenre(genreId, titleId));

        genreEntity.addTitleEntity(titleEntity);

//        then
        Mockito.verify(genreRepository, Mockito.times(0))
                .save(genreEntity);

        Assertions.assertEquals(
                (String.format("Gene with [%s] id not found", genreId)),
                ex.getMessage());
    }
}