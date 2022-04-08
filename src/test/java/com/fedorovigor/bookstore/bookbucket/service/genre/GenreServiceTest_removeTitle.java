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
class GenreServiceTest_removeTitle {

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

        titleEntity = new TitleEntity();
        titleEntity.setId(titleId);

        genreEntity = new GenreEntity();
        genreEntity.setId(genreId);
        genreEntity.setGenreName(genreName);
        genreEntity.setTitles(new ArrayList<>());
        genreEntity.addTitleEntity(titleEntity);
    }



    @Test
    public void shouldRemoveTitleFromGenre() {
//        1. Mock genreRepository.findById
//          1.1 if empty throw ResourceNotFoundException(
//                        String.format("Genre with [%s] id not found", genreId))
//        2. Mock titleRepository.findById
//          2.1 if empty throw ResourceNotFoundException(
//                        String.format("Title with [%s] id not found", titleId)
//        3. should save genre without title

        Mockito.when(genreRepository.findById(genreId))
                .thenReturn(Optional.of(genreEntity));

        Mockito.when(titleRepository.findById(titleId))
                .thenReturn(Optional.of(titleEntity));

        mocked.removeTitleFromGenre(genreId, titleId);

        Mockito.verify(genreRepository).save(genreCaptor.capture());

        Assertions.assertEquals(false,
                genreCaptor.getValue().getTitles().contains(titleEntity));
    }

    @Test
    public void shouldThrow_whenGenreNull() {

        Mockito.when(genreRepository.findById(genreId))
                .thenReturn(Optional.empty());

        var ex = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> mocked.removeTitleFromGenre(genreId, titleId));

        genreEntity.removeTitle(titleEntity);

//        then
        Mockito.verify(genreRepository, Mockito.times(0))
                .save(genreEntity);

        Assertions.assertEquals(
                (String.format("Genre with [%s] id not found", genreId)),
                ex.getMessage());
    }

    @Test
    public void shouldThrow_whenTitleNull() {

        Mockito.when(genreRepository.findById(genreId))
                .thenReturn(Optional.of(genreEntity));

        Mockito.when(titleRepository.findById(titleId))
                .thenReturn(Optional.empty());

        var ex = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> mocked.removeTitleFromGenre(genreId, titleId));

        genreEntity.removeTitle(titleEntity);

//        then
        Mockito.verify(genreRepository, Mockito.times(0))
                .save(genreEntity);

        Assertions.assertEquals(
                (String.format("Title with [%s] id not found", titleId)),
                ex.getMessage());
    }
}