package com.example.authorize.bookbucket.repository;

import com.example.authorize.bookbucket.model.entity.GenreEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GenreRepository extends CrudRepository<GenreEntity, Integer> {
    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END " +
            "FROM GenreEntity g WHERE g.genreName = :genre")
    boolean isExistsByName(@Param("genre") String genre);
}
