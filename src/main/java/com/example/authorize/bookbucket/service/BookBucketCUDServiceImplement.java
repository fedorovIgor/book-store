package com.example.authorize.bookbucket.service;

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
            saveTitlesWithGenre(request.getWrittenBooks());
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

    @Transactional( rollbackFor = {SQLException.class })
    private void saveTitlesWithGenre(List<TitleRequest> requests) {
//        1. check In titleName
//        2. All title who not exist should be saving
//        3. create report

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


        titlesNotStored.stream()
                .forEach(t -> {
                        t.getGenres().stream()
                                .forEach(g -> {
                                    g.getTitles().add(t);
                                });
                });

        var genres = titlesNotStored.stream()
                .map(t -> t.getGenres())
                .flatMap(Set::stream)
                .collect(Collectors.toList());

        Map<String, GenreEntity> genreMapWithAllTitles = new HashMap<>();

        for(GenreEntity g : genres) {
            if (genreMapWithAllTitles.containsKey(g.getGenreName())) {
                genreMapWithAllTitles.get(g.getGenreName())
                        .getTitles().addAll(g.getTitles());
            }
            else
                genreMapWithAllTitles.put(g.getGenreName(), g);
        }

        var genresRequestNames = genres.stream()
                .map(g -> g.getGenreName())
                .collect(Collectors.toSet());

        List<String> genreAlreadyStored = genreRepository.findAllInName(genresRequestNames);

        var genresToSave = genres.stream()
                .filter(g -> !genreAlreadyStored.contains(g.getGenreName()))
                .collect(Collectors.toSet());

        genresToSave = genresToSave.stream()
                .map(g -> {
                    g.setTitles(titlesNotStored);
                    return g;
                })
                .collect(Collectors.toSet());

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
