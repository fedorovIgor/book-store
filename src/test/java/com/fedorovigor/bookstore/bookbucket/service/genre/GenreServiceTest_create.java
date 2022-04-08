package com.fedorovigor.bookstore.bookbucket.service.genre;

import com.fedorovigor.bookstore.bookbucket.exception.ResourceAlreadyExistException;
import com.fedorovigor.bookstore.bookbucket.model.dto.GenreRequest;
import com.fedorovigor.bookstore.bookbucket.model.entity.GenreEntity;
import com.fedorovigor.bookstore.bookbucket.repository.GenreRepository;
import com.fedorovigor.bookstore.bookbucket.repository.TitleRepository;
import com.fedorovigor.bookstore.bookbucket.service.BucketMapping;
import com.fedorovigor.bookstore.bookbucket.service.GenreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest_create {
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private TitleRepository titleRepository;
    @Mock
    private BucketMapping mapping;

    private GenreService mocked;


    private GenreEntity entity;
    private GenreRequest request;

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

        String genreName = "GENRE";

        request = new GenreRequest(genreName);

       entity = new GenreEntity();
       entity.setGenreName(genreName);
    }

    @Test
    public void shouldSaveNewGenre() {
//        1. Mock genreRepository.isExistsByName()
//            1.1 if exist throw ResourceAlreadyExistException(String.format(
//                    "Genre with name [%s] already exist", request.getGenre()
//            )
//        2. Mock mapping.genreRequestToGenreEntity()
//        3. verify genreRepository.save()

        Mockito.when(genreRepository.isExistsByName(
                request.getGenre()))
                .thenReturn(false);

        Mockito.when(mapping.genreRequestToGenreEntity(request))
                .thenReturn(entity);

//        when
        mocked.createGenre(request);

//        then
        Mockito.verify(genreRepository).save(entity);
    }

    @Test
    public void shouldThrow_whenGenreExist() {

        Mockito.when(genreRepository.isExistsByName(
                request.getGenre()
        )).thenReturn(true);

//        when
        var ex = Assertions.assertThrows(
                ResourceAlreadyExistException.class,
                () -> mocked.createGenre(request));

//        then
        Mockito.verify(genreRepository, Mockito.times(0))
                .save(entity);

        Assertions.assertEquals(
                String.format("Genre with name [%s] already exist", request.getGenre()),
                ex.getMessage());
    }
}