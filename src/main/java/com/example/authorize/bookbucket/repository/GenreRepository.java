package com.example.authorize.bookbucket.repository;

import com.example.authorize.bookbucket.model.entity.GenreEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface GenreRepository extends CrudRepository<GenreEntity, Integer> {
    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END " +
            "FROM GenreEntity g WHERE g.genreName = :genre")
    boolean isExistsByName(@Param("genre") String genre);

    @Query("SELECT g FROM GenreEntity g " +
            "WHERE g.genreName IN (:genreNames)")
    List<GenreEntity> findAllInName(@Param("genreNames") Set<String> genres);

    @Query("SELECT g FROM GenreEntity g " +
            "JOIN FETCH g.titles t " +
            "WHERE t.id IN (:titlesId) " +
            "AND g.genre_name IN (:genreNames)")
    List<GenreEntity> findInNameAndTitlesId(@Param("genreNames") Set<String> genres,
                                            @Param("titlesId") List<Integer> titlesId);
}
