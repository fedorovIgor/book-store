package com.fedorovigor.bookstore.user.mapper;

import com.fedorovigor.bookstore.user.model.dto.Authority;
import com.fedorovigor.bookstore.user.model.dto.Role;
import com.fedorovigor.bookstore.user.model.dto.User;
import com.fedorovigor.bookstore.user.model.entity.AuthorityEntity;
import com.fedorovigor.bookstore.user.model.entity.RoleEntity;
import com.fedorovigor.bookstore.user.model.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public Authority authorityEntityToAuthority(AuthorityEntity entity) {

        var authority = new Authority();

        authority.setName(entity.getName());
        authority.setDescription(entity.getDescription());

        return authority;
    }

    public Role roleEntityToRole(RoleEntity entity) {
        var role = new Role();
        var authorities = entity.getAuthorities().stream()
                .map(this::authorityEntityToAuthority)
                .collect(Collectors.toList());

        role.setRoleName(entity.getRoleName());
        role.setAuthorities(authorities);

        return role;
    }


    public User userEntityToUser(UserEntity entity) {
        var user = new User();

        user.setUsername(entity.getUsername());
        user.setPassword(entity.getPassword());

        return user;
    }

    public User userEntityToUserWithAuthority(UserEntity entity) {
        var user = new User();

        user.setUsername(entity.getUsername());
        user.setPassword(entity.getPassword());

        if (entity.getRole() == null ||
                entity.getRole().getRoleName() == null ||
                entity.getRole().getAuthorities() == null ||
                entity.getRole().getAuthorities().isEmpty())
            return user;

        user.setGrantedAuthorities(getAuthoritiesByRoleEntity(entity.getRole()));

        return user;
    }

    public Collection<GrantedAuthority> getAuthoritiesByRoleEntity(RoleEntity role) {
        Collection<GrantedAuthority> grantedAuthorities = role.getAuthorities().stream()
                        .map(a -> new SimpleGrantedAuthority(a.getName()))
                        .collect(Collectors.toList());

        grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));

        return grantedAuthorities;
    }
}
