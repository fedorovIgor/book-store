package com.fedorovigor.bookstore.bookbucket.service;

import com.fedorovigor.bookstore.bookbucket.exception.IllegalAuthorRequestException;
import com.fedorovigor.bookstore.bookbucket.exception.ResourceAlreadyExistException;
import com.fedorovigor.bookstore.bookbucket.exception.ResourceNotFoundException;
import com.fedorovigor.bookstore.bookbucket.model.dto.AuthorRequest;
import com.fedorovigor.bookstore.bookbucket.model.entity.AuthorEntity;
import com.fedorovigor.bookstore.bookbucket.model.entity.TitleEntity;
import com.fedorovigor.bookstore.bookbucket.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final TitleService titlesService;

    public void addNewTitlesToExistAuthor(AuthorRequest request) {
//        1. check author by id
//        2.2 if booksId null - if title not exist save then add ids to author
//        3. save authorEntity with title ids
        if (request == null || request.getAuthorId() == null )
            throw new IllegalAuthorRequestException("AuthorId is null");

        if (request.getWrittenBooks() == null || request.getWrittenBooks().isEmpty())
            throw new IllegalAuthorRequestException("No One books present, nothing to add");

        AuthorEntity authorExist = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Author with id [%s] not exist", request.getAuthorId())));

        titlesService.saveNewTitles(request.getWrittenBooks());

        var titleNames = request.getWrittenBooks().stream()
                .map(t -> t.getTitleName())
                .collect(Collectors.toSet());

        Set<TitleEntity> titleEntity = titlesService.getTitlesIdsByName(titleNames);

        authorExist.setTitles(titleEntity);

        authorRepository.save(authorExist);
    }


    public void createAuthor(AuthorRequest request) {
//        1. check author by full name and date
//        2. save authorEntity

        if (request == null ||
            request.getFirstName() == null ||
            request.getLastName() == null ||
            request.getBirthday() == null )
            throw new IllegalAuthorRequestException(String.format("Some of fields are null, " +
                    "have to check firstName, lastName, birthday: [%s]", request));

        boolean isAuthorRequestExist = authorRepository.isExist(
                request.getFirstName(),
                request.getLastName(),
                request.getBirthday());

        if (isAuthorRequestExist)
            throw new ResourceAlreadyExistException(
                    String.format("Author [%s] already exist", request));

        authorRepository.save(authorRequestToEntity(request));
    }


    public void updateAuthor(Integer authorId, AuthorRequest request) {
//        1. check Author
//        2. update fields
//        3. save

        AuthorEntity entity = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        "Author with [%s] id not found", authorId
                )));

        entity = changeNotNullField(request, entity);

        authorRepository.save(entity);
    }

    public void addTitleToAuthor(Integer authorId, Integer titleId) {
        AuthorEntity entity = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        "Author with [%s] id not found", authorId
                )));

        TitleEntity titleEntity = titlesService.getTitleById(titleId);

        entity.addTitleEntity(titleEntity);

        authorRepository.save(entity);
    }

    private AuthorEntity changeNotNullField(AuthorRequest request, AuthorEntity entity) {
        if (request.getFirstName() != null)
            entity.setFirstName(request.getFirstName());
        if (request.getLastName() != null)
            entity.setLastName(request.getLastName());
        if (request.getBiography() != null)
            entity.setBiography(request.getBiography());
        if (request.getBirthday() != null)
            entity.setBirthday(request.getBirthday());

        return entity;
    }

    private AuthorEntity authorRequestToEntity(AuthorRequest authorRequest) {

        AuthorEntity e = new AuthorEntity();

        Set<TitleEntity> titles = new HashSet<>();
        e.setTitles(titles);
        e.setFirstName(authorRequest.getFirstName());
        e.setLastName(authorRequest.getLastName());
        e.setBirthday(authorRequest.getBirthday());
        e.setBiography(authorRequest.getBiography());

        return e;
    }
}
