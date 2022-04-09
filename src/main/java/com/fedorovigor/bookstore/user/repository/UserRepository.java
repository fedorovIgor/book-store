package com.fedorovigor.bookstore.user.repository;

import com.fedorovigor.bookstore.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    String queryFindUserByUsername = """
            SELECT u FROM UserEntity u
            LEFT JOIN FETCH u.role r
            LEFT JOIN FETCH r.authorities
            WHERE u.username LIKE (:username)""";
    @Query(queryFindUserByUsername)
    Optional<UserEntity> findUserWithAuthoritiesByUsername(String username);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.role")
    List<UserEntity> findAllUsersWithRole();


    @Modifying
    @Query("UPDATE UserEntity u SET u.password = :newPassword " +
            "WHERE u.username = :username")
    int updatePassword(@Param("newPassword") String newPassword, @Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
            "FROM UserEntity u WHERE u.username = :username")
    boolean isUserExistByUsername(@Param("username") String username);
}
