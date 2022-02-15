package com.example.authorize.user;

import com.example.authorize.user.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u " +
            "LEFT JOIN FETCH u.role r " +
            "LEFT JOIN FETCH r.authorities " +
            "WHERE u.username = :username")
    Optional<UserEntity> findUserByUsername(String username);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.role")
    List<UserEntity> findAllUsers();


}
