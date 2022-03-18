package com.example.authorize.bookbucket.service;

import com.example.authorize.bookbucket.exception.IllegalTitleRequestException;
import com.example.authorize.bookbucket.model.entity.GenreEntity;
import com.example.authorize.bookbucket.model.entity.TitleEntity;
import com.example.authorize.bookbucket.repository.AuthorRepository;
import com.example.authorize.bookbucket.repository.TitleRepository;
import com.example.authorize.bookbucket.model.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TitlesService{

    private final TitleRepository titleRepository;

    private final GenreService genreService;


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
    public void updateTitlesById(List<TitleRequest> requests) {

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

    public void deleteTitles(List<Integer> titlesIdsGiven) {
//        1. find if exist
//        2. delete from genres if not null
//        3. delete from authors if not null
//        4. delete titles

    }


    public Set<TitleEntity> getTitlesIdsByName(Set<String> titleNames) {
        return titleRepository.findTitlesInName(titleNames);
    }


    public TitleEntity titleRequestToTitleEntity(TitleRequest title) {
        TitleEntity e = new TitleEntity();

        Set<GenreEntity> genres = new HashSet<>();
        if (title.getGenre() != null) {
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
