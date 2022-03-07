package com.example.authorize.bookbucket.dao;

import com.example.authorize.bookbucket.model.entity.TitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TitleRepository extends JpaRepository<TitleEntity, Integer> {

    @Query("SELECT t FROM TitleEntity t " +
            "JOIN t.genres g " +
            "JOIN t.authors " +
            "WHERE g.genreName = :genre")
    List<TitleEntity> findTitlesInGenre(@Param("genre") String genre);

    @Query("SELECT t FROM TitleEntity t " +
            "JOIN t.genres " +
            "JOIN t.authors a " +
            "WHERE a.id = :authorId")
    List<TitleEntity> findTitlesInAuthor(@Param("authorId") Integer authorId);

    @Query("SELECT t FROM TitleEntity t " +
            "JOIN t.genres " +
            "JOIN t.authors " +
            "JOIN FETCH t.booksEntity " +
            "WHERE t.id = :titleId "
    )
    Optional<TitleEntity> findTitleWithBooks(@Param("titleId") Integer titleId);
}
