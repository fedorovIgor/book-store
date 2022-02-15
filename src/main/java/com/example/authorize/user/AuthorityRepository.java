package com.example.authorize.user;

import com.example.authorize.user.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Integer> {

    @Query("SELECT COUNT(*) FROM AuthorityEntity a WHERE a.name IN (:names)")
    int countAuthorityByName(@Param("names")Collection<String> authorityNames);

    @Query("SELECT a FROM AuthorityEntity a WHERE a.name IN (:names)")
    List<AuthorityEntity> authorityInName(@Param("names")Collection<String> authorityNames);
}
