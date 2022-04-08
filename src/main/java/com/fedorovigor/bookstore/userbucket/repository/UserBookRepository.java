package com.fedorovigor.bookstore.userbucket.repository;

import com.fedorovigor.bookstore.userbucket.model.entity.UserResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserResourceEntity, Integer> {
    String queryFindRelatedToUserBook = """
            SELECT ur FROM UserResourceEntity ur
            WHERE ur.username = (:username)""";
    @Query(queryFindRelatedToUserBook)
    List<UserResourceEntity> findRelatedToUserBook(@Param("username") String username);

    String queryFindRelatedIds = """
            SELECT ur.id FROM UserResourceEntity ur
            WHERE ur.username = (:username)""";
    @Query(queryFindRelatedIds)
    List<Integer> findRelatedIds(@Param("username") String username);
}
