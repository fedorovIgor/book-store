package com.fedorovigor.bookstore.bookbucket.repository;

import com.fedorovigor.bookstore.bookbucket.model.entity.BookEntity;
import com.fedorovigor.bookstore.bookbucket.model.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    String queryFindAllInId = """
            SELECT b FROM BookEntity b
            JOIN FETCH b.title
            WHERE b.id IN (:bookIds)""";
    @Query (queryFindAllInId)
    List<BookEntity> findAllInId(@Param("bookIds") List<Integer> ids);

}
