package com.example.authorize.bookbucket.service;

import com.example.authorize.bookbucket.model.entity.GenreEntity;
import com.example.authorize.bookbucket.repository.GenreRepository;
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
