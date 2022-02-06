package com.example.authorize.user.service;

import com.example.authorize.user.UserRepository;
import com.example.authorize.user.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        var user = new User(entity.getUsername(), entity.getPassword());

        var role = new Role(entity.getRole().getRoleName());
        var authority = List.of(new GrantedAuthorityRealization(role));

        return new SecurityUser(user, authority);
    }

    public List<SecurityUser> getAllUser() {

        var result = StreamSupport
                .stream(userRepository.findAll().spliterator(),false)
                .map(this::userEntityToSecurityUser)
                .collect(Collectors.toList());

        return result;
    }



    private SecurityUser userEntityToSecurityUser(UserEntity entity) {
        var user = new User(entity.getUsername(), entity.getPassword());

        var role = new Role(entity.getRole().getRoleName());
        var authority = List.of(new GrantedAuthorityRealization(role));

        return new SecurityUser(user, authority);
    }

}