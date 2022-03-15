package com.example.authorize.bookbucket.service;

import com.example.authorize.bookbucket.exception.IllegalTitleRequestException;
import com.example.authorize.bookbucket.model.entity.GenreEntity;
import com.example.authorize.bookbucket.model.entity.TitleEntity;
import com.example.authorize.bookbucket.repository.AuthorRepository;
import com.example.authorize.bookbucket.repository.BookRepository;
import com.example.authorize.bookbucket.repository.GenreRepository;
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
public class BookBucketCUDServiceImplement implements BookBucketCUDService{

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final TitleRepository titleRepository;
    private final GenreRepository genreRepository;

    public void createAuthorTitle(AuthorRequest request) {
        if (request.getAuthorId() != null) {
            addTitlesToAuthor(request);
        }
        if (request.getBirthday() == null &&
                request.getLastNam() == null) {
            saveNewTitles(request.getWrittenBooks());
        }
        else {
            saveAuthorWithTitle(request);
        }
    }

    private void saveAuthorWithTitle(AuthorRequest request){
        saveAuthor(request);
        addTitlesToAuthor(request);
    }

    private void addTitlesToAuthor(AuthorRequest request) {
//        1. check author by id
//        2.1 if booksId not null - if exist add to author
//        2.2 if booksId null - if title not exist save then add ids to author
//        3. save authorEntity with title ids
    }

    private void saveAuthor(AuthorRequest request) {
//        1. check author by name and date
//        2. if TitleRequest not null call saveTitles, add returned titleIds
//        3. save authorEntity
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

        List<String> titlesAlreadyStored = titleRepository.findTitlesInName(titleNames);

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
        addTitlesToGenre(genresWithTitles);

    }

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

        updateTitlesGere(genresWithTitles, titlesIds);

    }

    private void updateTitlesGere(List<GenreEntity> genreGiven, List<Integer> titlesId) {
        if (genreGiven == null || genreGiven.isEmpty())
            return;

        Map<String, GenreEntity> genreMapWithGivenTitles = new HashMap<>();

        for(GenreEntity g : genreGiven) {
            if  (g != null && g.getGenreName() != null) {
                if (genreMapWithGivenTitles.containsKey(g.getGenreName())) {
                    genreMapWithGivenTitles.get(g.getGenreName())
                            .getTitles().addAll(g.getTitles());
                } else
                    genreMapWithGivenTitles.put(g.getGenreName(), g);
            }
        }

        var genreGivenNames = genreGiven.stream()
                .map(g -> g.getGenreName())
                .collect(Collectors.toSet());

        var genreForUpdate = genreRepository.findInNameAndTitlesId(genreGivenNames, titlesId);

        for(var g : genreForUpdate) {
            g.getTitles().clear();
            g.getTitles().addAll(
                genreMapWithGivenTitles.get(g.getGenreName()).getTitles()
            );
        }

        genreRepository.saveAll(genreForUpdate);
    }


    private void addTitlesToGenre(List<GenreEntity> genreGiven) {

        if (genreGiven == null || genreGiven.isEmpty())
            return;

        Map<String, GenreEntity> genreMapWithAllTitles = new HashMap<>();

        for(GenreEntity g : genreGiven) {
            if  (g != null && g.getGenreName() != null) {
                if (genreMapWithAllTitles.containsKey(g.getGenreName())) {
                    genreMapWithAllTitles.get(g.getGenreName())
                            .getTitles().addAll(g.getTitles());
                } else
                    genreMapWithAllTitles.put(g.getGenreName(), g);
            }
        }

        List<GenreEntity> genreAlreadyStored = genreRepository
                .findAllInName(genreMapWithAllTitles.keySet());

        //add titles from existed genre to not saved
        for (var g : genreAlreadyStored) {
            var notStoredGenre = genreMapWithAllTitles.get(g.getGenreName());
            notStoredGenre.getTitles().addAll(g.getTitles());
            notStoredGenre.setId(g.getId());
        }

        genreRepository.saveAll(genreMapWithAllTitles.values());
    }

    private TitleEntity titleRequestToTitleEntity(TitleRequest title) {
        TitleEntity e = new TitleEntity();

        Set<GenreEntity> genres = new HashSet<>();
        if (title.getGenre() != null) {
            genres = title.getGenre().stream()
                    .map(g -> g.getGenre())
                    .map(this::genreToEntity)
                    .collect(Collectors.toSet());
        }

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
