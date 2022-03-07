package com.example.authorize.bookbucket.dao;

import com.example.authorize.bookbucket.model.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

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
}
