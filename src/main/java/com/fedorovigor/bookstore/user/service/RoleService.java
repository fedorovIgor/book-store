package com.fedorovigor.bookstore.user.service;

import com.fedorovigor.bookstore.user.mapper.UserMapper;
import com.fedorovigor.bookstore.user.repository.AuthorityRepository;
import com.fedorovigor.bookstore.user.repository.RoleRepository;
import com.fedorovigor.bookstore.user.model.dto.Authority;
import com.fedorovigor.bookstore.user.model.entity.AuthorityEntity;
import com.fedorovigor.bookstore.user.model.dto.Role;
import com.fedorovigor.bookstore.user.model.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final UserMapper mapper;

    public List<Role> getAllRolesWithAuthorities() {
        var roles = roleRepository.findAllRolesWithAuthorities().stream()
                .map(this::roleEntityToRole)
                .collect(Collectors.toList());

        return roles;
    }


    public Role findRoleByName(String roleName) {
        RoleEntity entity = roleRepository.findRoleByName(roleName)
                .orElseThrow(() -> new RuntimeException(
                        String.format("cant get role with name [%s]", roleName))
        );

        Role role = roleEntityToRole(entity);

        return role;
    }


    public List<String> getRoleNames() {
        var result = StreamSupport
                .stream(roleRepository.findAll().spliterator(), false)
                .map(RoleEntity::getRoleName)
                .collect(Collectors.toList());

        return result;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void changeRole(Role role) {
        RoleEntity roleEntity = roleRepository.findRoleByName(role.getRoleName())
                .orElseThrow(() -> new RuntimeException(
                        String.format("cant get role with name [%s]", role.getRoleName()))
                );

        if (!isAllAuthoritiesExist(role.getAuthorities()))
            throw new RuntimeException(
                    String.format("given authorities not exist, Authorities [%s]",role.getAuthorities())
            );

        roleEntity.setAuthorities(this.authoritiesToEntity(role.getAuthorities()));

        roleRepository.save(roleEntity);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void createNewRole(Role role) {
        if (role == null || role.getRoleName() == null)
            throw new RuntimeException("Role may not be empty or null");

        Optional<RoleEntity> roleOptional = roleRepository.findRoleByName(role.getRoleName());

        if (roleOptional.isPresent())
            throw new RuntimeException(
                    String.format("Role with name [%s] already exist", role.getRoleName())
            );

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleName(role.getRoleName());

        if (role.getAuthorities() == null || role.getAuthorities().size() == 0) {
            roleRepository.save(roleEntity);
        }
        else {
            if (!isAllAuthoritiesExist(role.getAuthorities()))
                throw new RuntimeException(String
                        .format("given authorities not exist, Authorities [%s]", role.getAuthorities()));

            roleEntity.setAuthorities(this.authoritiesToEntity(role.getAuthorities()));

            roleRepository.save(roleEntity);
        }
    }


    private Role roleEntityToRole(RoleEntity entity) {
        var role = new Role();
        var authorities = entity.getAuthorities().stream()
                        .map(this::authorityEntityToAuthority)
                        .collect(Collectors.toList());

        role.setRoleName(entity.getRoleName());
        role.setAuthorities(authorities);

        return role;
    }


    private Authority authorityEntityToAuthority(AuthorityEntity entity) {

        var authority = new Authority();

        authority.setName(entity.getName());
        authority.setDescription(entity.getDescription());

        return authority;
    }

    private boolean isAllAuthoritiesExist(Collection<Authority> authorities) {
        var givenRolesNames = authorities.stream()
                .map(a -> a.getName())
                .collect(Collectors.toList());

        int givenSize = authorities.size();

        int existedSize = authorityRepository.countAuthorityByName(givenRolesNames);

        return givenSize == existedSize;
    }

    private Collection<AuthorityEntity> authoritiesToEntity(Collection<Authority> authorities) {
        var givenRolesNames = authorities.stream()
                .map(a -> a.getName())
                .collect(Collectors.toList());

        return authorityRepository.authorityInName(givenRolesNames);
    }

}
