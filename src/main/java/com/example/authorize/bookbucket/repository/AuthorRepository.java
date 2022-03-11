package com.example.authorize.bookbucket.repository;

import com.example.authorize.bookbucket.model.entity.AuthorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END " +
            "FROM AuthorEntity a " +
            "WHERE a.firstName = :firstName AND " +
            "a.lastName = :lastName AND " +
            "a.birthday = :birthday "
    )
    boolean isExist(@Param("firstName") String firstName,
                    @Param("lastName") String LastName,
                    @Param("birthday")LocalDate birthday);

    @Query("SELECT DISTINCT a " +
            "FROM AuthorEntity a " +
            "LEFT JOIN FETCH a.titles t " +
            "WHERE a.id IN (:ids)")
    List<AuthorEntity> getAuthorsInIds(@Param("ids") List<Integer> ids);

    @Query("SELECT a.id FROM AuthorEntity a ")
    Page<Integer> findAuthorsIds(Pageable pageable);


}
