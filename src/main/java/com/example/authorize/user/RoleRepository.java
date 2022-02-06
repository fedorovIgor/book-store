package com.example.authorize.user;

import com.example.authorize.user.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {

    @Query("SELECT r FROM RoleEntity r WHERE r.roleName = :roleName")
    Optional<RoleEntity> findByRoleName(String roleName);
}
