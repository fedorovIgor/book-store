package com.fedorovigor.bookstore.user.repository;

import com.fedorovigor.bookstore.user.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {

    @Query("SELECT r FROM RoleEntity r WHERE r.roleName = :roleName")
    Optional<RoleEntity> findRoleByName(String roleName);

    @Query("SELECT DISTINCT r FROM RoleEntity r INNER JOIN r.authorities")
    List<RoleEntity> findAllRolesWithAuthorities();


    @Query("SELECT r FROM RoleEntity r LEFT JOIN FETCH r.authorities WHERE r.roleName = :roleName")
    Optional<RoleEntity> findAllRoleWithAuthoritiesByName(String roleName);
}
