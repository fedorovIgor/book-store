package com.fedorovigor.bookstore.bookbucket.service.author;

import com.fedorovigor.bookstore.bookbucket.exception.IllegalAuthorRequestException;
import com.fedorovigor.bookstore.bookbucket.exception.ResourceAlreadyExistException;
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

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest_create {

    @Mock
    AuthorRepository authorRepository;
    @Mock
    private TitleService titlesService;

    private AuthorService mocked;

    private AuthorRequest request;
    private AuthorEntity entity;

    @BeforeEach
    public  void init() {
        mocked = new AuthorService(
                authorRepository,
                titlesService);

        initEntities();
    }

    private void initEntities() {

        String firstName = "name";
        String lastName = "LastName";
        LocalDate birthday = LocalDate.now();

        request = new AuthorRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setBirthday(birthday);

        entity = new AuthorEntity();
        entity.setBirthday(birthday);
        entity.setLastName(lastName);
        entity.setFirstName(firstName);
    }

    @Test
    public void shouldCreateAuthor() {
//        1. create AuthorRequest
//        2. check null fields - should throw IllegalAuthorRequestException(String.format("Some of fields are null, " +
//                    "have to check firstName, lastName, birthday: [%s]", request)
//        3. Mock authorRepository.isExist - should return false to pass
//          3.1 if returned true - throw ResourceAlreadyExistException(
//                    String.format("Author [%s] already exist", request)
//        4. Mock authorRepository.save() have called

        String firstName = "name";
        String lastName = "LastName";
        LocalDate birthday = LocalDate.now();

        Mockito.when(authorRepository.isExist(
                firstName,
                lastName,
                birthday
        )).thenReturn(false);

//        when
        mocked.createAuthor(request);

//        then
        Mockito.verify(authorRepository).save(entity);

    }

    @Test
    public void shouldThrow_whenAuthorFieldNull() {

        LocalDate birthday = null;
        request.setBirthday(birthday);

//        when
        var ex = Assertions.assertThrows(IllegalAuthorRequestException.class, () -> {
            mocked.createAuthor(request);
        } );

//        then
        entity.setBirthday(birthday);

        Mockito.verify(authorRepository, Mockito.times(0))
                .save(entity);

        Assertions.assertEquals((String.format("Some of fields are null, " +
                "have to check firstName, lastName, birthday: [%s]", request)), ex.getMessage());
    }

    @Test
    public void shouldThrow_whenAuthorAlreadyExist() {

        String firstName = "name";
        String lastName = "LastName";
        LocalDate birthday = LocalDate.now();

        Mockito.when(authorRepository.isExist(
                firstName,
                lastName,
                birthday
        )).thenReturn(true);

        var ex = Assertions.assertThrows(ResourceAlreadyExistException.class, () -> {
            mocked.createAuthor(request);
        } );

//        then

        Mockito.verify(authorRepository, Mockito.times(0))
                .save(entity);

        Assertions.assertEquals(String.format("Author [%s] already exist", request), ex.getMessage());
    }
}