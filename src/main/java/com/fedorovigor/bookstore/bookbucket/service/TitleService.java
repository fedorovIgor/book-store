package com.fedorovigor.bookstore.bookbucket.service;

import com.fedorovigor.bookstore.bookbucket.exception.IllegalTitleRequestException;
import com.fedorovigor.bookstore.bookbucket.exception.ResourceAlreadyExistException;
import com.fedorovigor.bookstore.bookbucket.exception.ResourceNotFoundException;
import com.fedorovigor.bookstore.bookbucket.model.dto.BookRequest;
import com.fedorovigor.bookstore.bookbucket.model.dto.TitleRequest;
import com.fedorovigor.bookstore.bookbucket.model.dto.TitleResponse;
import com.fedorovigor.bookstore.bookbucket.model.entity.BookEntity;
import com.fedorovigor.bookstore.bookbucket.model.entity.GenreEntity;
import com.fedorovigor.bookstore.bookbucket.model.entity.TitleEntity;
import com.fedorovigor.bookstore.bookbucket.repository.TitleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TitleService implements TitleCommunication{

    private final TitleRepository titleRepository;
    private final GenreService genreService;
    private final BucketMapping mapper;

    @Override
    public List<TitleResponse> getAllTitleWithAuthorGenresByIds(List<Integer> ids) {
        var fullTitleEntity = titleRepository.findTitlesInIds(ids);

        var titles = fullTitleEntity.stream()
                .map(mapper::titleEntityToFullTitleResponse)
                .collect(Collectors.toList());

        return titles;
    }

    public void createTitle(TitleRequest request) {

        Optional<TitleEntity> titleOptional = titleRepository
                .findeTitleByName(request.getTitleName());

        if (titleOptional.isPresent())
            throw new ResourceAlreadyExistException(String.format(
                    "Title with name [%s] already exist, title: [%s]",
                    request.getTitleName(),
                    request
            ));

        TitleEntity entity = mapper.titleRequestToTitleEntity(request);

        titleRepository.save(entity);
    }


    public TitleEntity getTitleById(Integer titleId) {
        return titleRepository.findById(titleId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        "Title with [%s] id not found", titleId
                )));
    }

    public void addBookToTitle(Integer titleId, BookRequest request) {
        TitleEntity titleEntity = this.getTitleById(titleId);
        BookEntity bookEntity = mapper.bookRequestToBookEntity(request);

        titleEntity.addBook(bookEntity);

        titleRepository.save(titleEntity);
    }


    @Transactional( rollbackFor = { SQLException.class })
    public void saveNewTitles(List<TitleRequest> requests) {

        if (requests == null || requests.isEmpty())
            throw new IllegalTitleRequestException("Request is empty");

        var titlesFromRequest = requests.stream()
                .filter(t -> t.getTitleId() == null)
                .map(this::titleRequestToTitleEntity)
                .collect(Collectors.toList());

        var titleNames = titlesFromRequest.stream()
                .map( t -> t.getName())
                .collect(Collectors.toSet());

        List<String> titlesAlreadyStored = titleRepository.findTitlesInName(titleNames)
                .stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());

        var titlesNotStored = titlesFromRequest.stream()
                .filter(t -> !titlesAlreadyStored.contains(t.getName()))
                .collect(Collectors.toList());

        titleRepository.saveAll(titlesNotStored);

        // add titles for every genre
        titlesNotStored.stream()
            .forEach(t -> {
                t.getGenres().stream()
                    .forEach(g -> {
                        g.getTitles().add(t);
                    });
            });

        var genresWithTitles = titlesNotStored.stream()
                .map(t -> t.getGenres())
                .flatMap(Set::stream)
                .collect(Collectors.toList());

        if(genresWithTitles.isEmpty())
            return;
        genreService.addTitlesToGenre(genresWithTitles);

    }

    @Transactional( rollbackFor = { SQLException.class })
    public void updateTitles(List<TitleRequest> requests) {

        if (requests == null || requests.isEmpty())
            throw new IllegalTitleRequestException("Request is empty");

        var titlesFromRequest = requests.stream()
                .filter(t -> t.getTitleId() != null)
                .map(this::titleRequestToTitleEntity)
                .collect(Collectors.toList());

        titleRepository.saveAll(titlesFromRequest);

//        updateTitlesGere
        titlesFromRequest.stream()
                .forEach(t -> {
                    t.getGenres().stream()
                            .forEach(g -> {
                                g.getTitles().add(t);
                            });
                });

        var genresWithTitles = titlesFromRequest.stream()
                .map(t -> t.getGenres())
                .flatMap(Set::stream)
                .collect(Collectors.toList());

        var titlesIds = titlesFromRequest.stream()
                .map(t -> t.getId())
                .collect(Collectors.toList());

        genreService.updateTitlesGere(genresWithTitles, titlesIds);
    }

    public Set<TitleEntity> getTitlesIdsByName(Set<String> titleNames) {
        return titleRepository.findTitlesInName(titleNames);
    }

    public TitleEntity titleRequestToTitleEntity(TitleRequest title) {
        TitleEntity e = new TitleEntity();

        Set<GenreEntity> genres = new HashSet<>();
        if (title.getGenre() != null && !title.getGenre().isEmpty()) {
            genres = title.getGenre().stream()
                    .map(g -> g.getGenre())
                    .map(this::genreToEntity)
                    .collect(Collectors.toSet());
        }

        e.setId(title.getTitleId());
        e.setGenres(genres);
        e.setDateWriting(title.getDataWrite());
        e.setImageUrl(title.getImgUrl());
        e.setName(title.getTitleName());

        return e;
    }

    private GenreEntity genreToEntity(String genre) {
        GenreEntity g = new GenreEntity();
        g.setGenreName(genre.toUpperCase());
        g.setTitles(new ArrayList<TitleEntity>());

        return g;
    }
}
