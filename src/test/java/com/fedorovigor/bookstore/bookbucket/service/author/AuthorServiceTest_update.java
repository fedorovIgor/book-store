package com.fedorovigor.bookstore.bookbucket.service.author;

import com.fedorovigor.bookstore.bookbucket.exception.ResourceNotFoundException;
import com.fedorovigor.bookstore.bookbucket.model.dto.AuthorRequest;
import com.fedorovigor.bookstore.bookbucket.model.entity.AuthorEntity;
import com.fedorovigor.bookstore.bookbucket.repository.AuthorRepository;
import com.fedorovigor.bookstore.bookbucket.service.AuthorService;
import com.fedorovigor.bookstore.bookbucket.service.TitleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest_update {

    @Mock
    AuthorRepository authorRepository;
    @Mock
    private TitleService titlesService;

    private AuthorService mocked;

    private Integer authorId;
    private AuthorRequest testRequest;
    private AuthorEntity oldEntity;
    private AuthorEntity updatedEntity;

    @BeforeEach
    public  void init() {
        mocked = new AuthorService(
                authorRepository,
                titlesService);

        initEntities();
    }

    private void initEntities() {
        authorId = 1;

        String firstName = "newName";
        String lastName = "newLastName";
        LocalDate birthday = LocalDate.now();
        String biography = "n";

        testRequest = new AuthorRequest();
        testRequest.setFirstName(firstName);
        testRequest.setLastName(lastName);
        testRequest.setBiography("n");
        testRequest.setBirthday(LocalDate.of(1991, Month.DECEMBER,2));

        oldEntity = new AuthorEntity();
        oldEntity.setBirthday(birthday);
        oldEntity.setLastName("oldLastName");
        oldEntity.setFirstName("oldFirstName");

        updatedEntity = oldEntity;
        updatedEntity.setFirstName(firstName);
        updatedEntity.setLastName(lastName);
    }

    @Test
    public void updateAuthor() {
//        1. Mock authorRepository.findById(authorId) - return Optional<AuthorEntity>
//          1.1 if null -> throw ResourceNotFoundException(String.format(
//                        "Author with [%s] id not found", authorId
//                ))
//        2. change entity fields with not null request fields
//        3. save entity

        Mockito.when(authorRepository.findById(authorId))
                        .thenReturn(Optional.of(oldEntity));

//        when
        mocked.updateAuthor(authorId, testRequest);

//        then
        Mockito.verify(authorRepository).save(oldEntity);
    }

    @Test
    public void throw_whenAuthorNotExist() {

        Mockito.when(authorRepository.findById(authorId))
                        .thenReturn(Optional.of(oldEntity));

//        when
        Mockito.when(authorRepository.findById(authorId))
                .thenReturn(Optional.empty());

        var ex = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> {
            mocked.updateAuthor(authorId, testRequest);
        } );

//        then
        Mockito.verify(authorRepository,
                Mockito.times(0))
                .save(oldEntity);


        Assertions.assertEquals((String.format(
                "Author with [%s] id not found", authorId
        )), ex.getMessage());
    }
}