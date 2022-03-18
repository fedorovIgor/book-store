package com.example.authorize.bookbucket.repository;

import com.example.authorize.bookbucket.model.entity.TitleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TitleRepository extends JpaRepository<TitleEntity, Integer> {

    @Query("SELECT t FROM TitleEntity t " +
            "JOIN t.genres " +
            "JOIN t.authors " +
            "JOIN FETCH t.booksEntity " +
            "WHERE t.id = :titleId "
    )
    Optional<TitleEntity> findTitleWithBooks(@Param("titleId") Integer titleId);



    @Query("SELECT DISTINCT t FROM TitleEntity t " +
            "LEFT JOIN t.genres " +
            "LEFT JOIN t.authors " +
            "LEFT JOIN FETCH t.booksEntity " +
            "WHERE t.id IN (:ids) "
    )
    List<TitleEntity> findTitlesInIds(@Param("ids") List<Integer> ids);

    @Query("SELECT t.id FROM TitleEntity t " +
            "JOIN t.genres g " +
            "WHERE g.genreName = :genre")
    Page<Integer> findTitleIdsInGenre(@Param("genre") String genre, Pageable pageable);

    @Query("SELECT t.id FROM TitleEntity t " +
            "JOIN t.authors a " +
            "WHERE a.id = :authorId")
    Page<Integer> findTitleIdsInAuthor(@Param("authorId") int authorId, Pageable pageable);

    @Query("SELECT t.id FROM TitleEntity t ")
    Page<Integer> findTitleIds(Pageable pageable);

    @Query("SELECT t.name FROM TitleEntity t " +
            "WHERE t.name IN (:titles)")
    Set<TitleEntity> findTitlesInName(@Param("titles") Set<String> titleNames);
}
