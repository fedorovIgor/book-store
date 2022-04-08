package com.fedorovigor.bookstore.bookbucket.service;

import com.fedorovigor.bookstore.bookbucket.model.dto.*;
import com.fedorovigor.bookstore.bookbucket.model.entity.AuthorEntity;
import com.fedorovigor.bookstore.bookbucket.model.entity.BookEntity;
import com.fedorovigor.bookstore.bookbucket.model.entity.GenreEntity;
import com.fedorovigor.bookstore.bookbucket.model.entity.TitleEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BucketMapping {

    public TitleResponse titleEntityTitleResponse(TitleEntity e) {
        TitleResponse response = new TitleResponse().builder()
                .id(e.getId())
                .title(e.getName())
                .imgUrl(e.getImageUrl())
                .dateWriting(e.getDateWriting())
                .build();

        return response;
    }

    public TitleResponse titleEntityToFullTitleResponse(TitleEntity e) {
        TitleResponse response = new TitleResponse().builder()
                .id(e.getId())
                .title(e.getName())
                .imgUrl(e.getImageUrl())
                .dateWriting(e.getDateWriting())
                .authorNames(createShorAuthorName(e.getAuthors()))
                .genres(creteShorGenreName(e.getGenres()))
                .build();

        return response;
    }

    public TitleEntity titleRequestToTitleEntity(TitleRequest request) {
        TitleEntity e = new TitleEntity();

        e.setDateWriting(request.getDataWrite());
        e.setImageUrl(request.getImgUrl());
        e.setName(request.getTitleName());

        return e;
    }

    public GenreResponse genreEntityToGenre(GenreEntity e) {
        GenreResponse g = new GenreResponse().builder()
                .id(e.getId())
                .genre(e.getGenreName())
                .build();

        return g;
    }

    public BookEntity bookRequestToBookEntity(BookRequest request) {
        BookEntity e = new BookEntity();
        e.setPrice(request.getPrice());
        e.setDatePublication(request.getDatePublication());
        e.setPublisher(request.getPublisher());

        return e;
    }

    public BookDTO bookEntityToBookDTO(BookEntity e) {
        BookDTO bookDTO = new BookDTO().builder()
                .id(e.getId())
                .datePublication(e.getDatePublication())
                .downloadUrl(e.getDownloadUrl())
                .title(e.getTitle())
                .publisher(e.getPublisher())
                .price(e.getPrice())
                .build();

        return bookDTO;
    }

    public GenreEntity genreRequestToGenreEntity(GenreRequest request) {
        GenreEntity g = new GenreEntity();
        g.setGenreName(request.getGenre());

        return g;
    }

    public AuthorResponse authorEntityToAuthorResponse(AuthorEntity e) {
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setFirstName(e.getFirstName());
        authorResponse.setLastName(e.getLastName());
        authorResponse.setId(e.getId());

        return authorResponse;
    }

    public String createShorAuthorName(Set<AuthorEntity> e) {
        var result = e.stream()
                .map(a -> a.getFirstName().charAt(0) + "." + a.getLastName())
                .collect(Collectors.joining(","));

        return result;
    }

    public String creteShorGenreName(Collection<GenreEntity> names) {
        var result = names.stream()
                .map(n -> n.getGenreName())
                .collect(Collectors.joining(","));

        return result;
    }

}
