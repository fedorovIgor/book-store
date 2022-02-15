package com.example.authorize.user.service;

import com.example.authorize.user.RoleRepository;
import com.example.authorize.user.UserRepository;
import com.example.authorize.user.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<UserResponse> getAllUser() {

        var result = StreamSupport
                .stream(userRepository.findAllUsers().spliterator(),false)
                .map(this::userEntityToUserResponse)
                .collect(Collectors.toList());

        return result;
    }

    public UserResponse getUserByUsername(String username) {
        var entity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException
                (String.format("User with username [%s] not found", username)));

        return userEntityToUserResponse(entity);
    }


    public void changeUserRole(String username, String roleName) {

        UserEntity userEntity = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException
                (String.format("User with username [%s] not found", username)));

        RoleEntity roleEntity = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new UsernameNotFoundException
                (String.format("Role with roleName [%s] not found", roleName)));

        userEntity.setRole(roleEntity);

        userRepository.save(userEntity);
    }

    private UserResponse userEntityToUserResponse(UserEntity entity) {

        var result = new UserResponse(
                entity.getUsername(),
                entity.getRole().getRoleName());

        return result;
    }
}
