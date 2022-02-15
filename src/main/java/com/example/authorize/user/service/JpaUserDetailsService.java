package com.example.authorize.user.service;

import com.example.authorize.user.UserRepository;
import com.example.authorize.user.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Optional<UserEntity> userByUsername = userRepository.findUserByUsername(username);

        UserEntity entity = userByUsername.orElseThrow(() -> new UsernameNotFoundException
                (String.format("User with username [%s] not found", username)));

       var user = this.userEntityToUser(entity);

        return new SecurityUser(user);
    }


    private User userEntityToUser(UserEntity entity) {
        var user = new User();

        var roleEntity = entity.getRole();
        Collection<AuthorityEntity> authoritiesEntity = roleEntity.getAuthorities();

        var role = new Role();
        role.setRoleName(roleEntity.getRoleName());
        role.setAuthorities(
                authoritiesEntity.stream()
                        .map(a -> new Authority(a.getName(), a.getDescription()))
                        .collect(Collectors.toList())
        );

        user.setUsername(entity.getUsername());
        user.setPassword(entity.getPassword());
        user.setRole(role);

        return user;
    }

}