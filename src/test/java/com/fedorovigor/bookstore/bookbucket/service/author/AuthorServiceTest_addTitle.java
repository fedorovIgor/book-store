package com.fedorovigor.bookstore.bookbucket.service.author;

import com.fedorovigor.bookstore.bookbucket.exception.ResourceNotFoundException;
import com.fedorovigor.bookstore.bookbucket.model.entity.AuthorEntity;
import com.fedorovigor.bookstore.bookbucket.model.entity.TitleEntity;
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
import java.util.HashSet;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest_addTitle {

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private TitleService titlesService;

    private AuthorService mocked;


    private TitleEntity titleEntity;
    private AuthorEntity authorEntity;
    private Integer authorId;
    private Integer titleId;

    @BeforeEach
    public  void init() {
        mocked = new AuthorService(
                authorRepository,
                titlesService);

        initEntities();
    }

    private void initEntities() {

        authorId = 1;
        titleId = 1;

        String firstName = "name";
        String lastName = "LastName";
        LocalDate birthday = LocalDate.now();

        authorEntity = new AuthorEntity();
        authorEntity.setBirthday(birthday);
        authorEntity.setLastName(lastName);
        authorEntity.setFirstName(firstName);
        authorEntity.setTitles(new HashSet<>());
        authorEntity.setId(authorId);

        titleEntity = new TitleEntity();
        titleEntity.setId(titleId);

    }

    @Test
    public void shouldAddTitle() {
//        1. Mock authorRepository.findById(authorId)
//          1.1 if Optional.empty ResourceNotFoundException(String.format(
//                        "Author with [%s] id not found", authorId
//                ))
//        2. Mock titlesService.getTitleById(titleId)
//        3. add Title to entity and save

        Mockito.when(authorRepository.findById(authorId))
                .thenReturn(Optional.of(authorEntity));

        Mockito.when(titlesService.getTitleById(titleId))
                .thenReturn(titleEntity);

//        when
        mocked.addTitleToAuthor(authorId, titleId);

        authorEntity.addTitleEntity(titleEntity);

//        then
        Mockito.verify(authorRepository).save(authorEntity);
    }

    @Test
    public void shouldThrow_whenAuthorNotExist() {

        Mockito.when(authorRepository.findById(authorId))
                .thenReturn(Optional.empty());

//        when
        var ex = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> {
            mocked.addTitleToAuthor(authorId, titleId);
        } );

        authorEntity.addTitleEntity(titleEntity);

//        then
        Mockito.verify(authorRepository, Mockito.times(0))
                .save(authorEntity);

        Assertions.assertEquals(
                (String.format("Author with [%s] id not found", authorId)),
                ex.getMessage());
    }
}