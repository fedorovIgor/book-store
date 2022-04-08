package com.fedorovigor.bookstore.bookbucket.service;

import com.fedorovigor.bookstore.bookbucket.exception.ResourceAlreadyExistException;
import com.fedorovigor.bookstore.bookbucket.exception.ResourceNotFoundException;
import com.fedorovigor.bookstore.bookbucket.model.dto.GenreRequest;
import com.fedorovigor.bookstore.bookbucket.model.entity.GenreEntity;
import com.fedorovigor.bookstore.bookbucket.model.entity.TitleEntity;
import com.fedorovigor.bookstore.bookbucket.repository.GenreRepository;
import com.fedorovigor.bookstore.bookbucket.repository.TitleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final TitleRepository titleRepository;
    private final BucketMapping mapping;

    public void createGenre(GenreRequest request) {
        boolean isExist = genreRepository.isExistsByName(request.getGenre());

        if(isExist)
            throw new ResourceAlreadyExistException(String.format(
                    "Genre with name [%s] already exist", request.getGenre()
            ));

        GenreEntity entity = mapping.genreRequestToGenreEntity(request);

        genreRepository.save(entity);
    }

    public void addTitleToGenre(Integer genreId, Integer titleId) {
        TitleEntity titleEntity = titleRepository.findById(titleId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        "Title with [%s] id not found", titleId
                )));

        GenreEntity entity = genreRepository.findById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Gene with [%s] id not found", genreId)));

        entity.addTitleEntity(titleEntity);

        genreRepository.save(entity);
    }

    public void removeTitleFromGenre(Integer genreId, Integer titleId) {
//        1. get genreById
//          1.1 if not exist throw ResourceNotFoundException
//        2. get titleById
//          2.1 if not exist throw ResourceNotFoundException
//        3. remove title from genre
//        4. commit

        GenreEntity genreEntity = genreRepository.findById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        "Genre with [%s] id not found", genreId
                )));

        TitleEntity titleEntity = titleRepository.findById(titleId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        "Title with [%s] id not found", titleId
                )));

        genreEntity.removeTitle(titleEntity);

        genreRepository.save(genreEntity);
    }



    public void updateTitlesGere(List<GenreEntity> genreGiven, List<Integer> titlesId) {
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


    public void addTitlesToGenre(List<GenreEntity> genreGiven) {

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



}
