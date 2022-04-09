package com.fedorovigor.bookstore.user.service;

import com.fedorovigor.bookstore.user.repository.RoleRepository;
import com.fedorovigor.bookstore.user.repository.UserRepository;
import com.fedorovigor.bookstore.user.model.dto.UserResponse;
import com.fedorovigor.bookstore.user.model.entity.RoleEntity;
import com.fedorovigor.bookstore.user.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<UserResponse> getAllUser() {

        var result = StreamSupport
                .stream(userRepository.findAllUsersWithRole().spliterator(),false)
                .map(this::userEntityToUserResponse)
                .collect(Collectors.toList());

        return result;
    }

    public UserResponse getUserByUsername(String username) {
        var entity = userRepository.findUserWithAuthoritiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException
                (String.format("User with username [%s] not found", username)));

        return userEntityToUserResponse(entity);
    }


    public void changeUserRole(String username, String roleName) {

        UserEntity userEntity = userRepository.findUserWithAuthoritiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException
                (String.format("User with username [%s] not found", username)));

        RoleEntity roleEntity = roleRepository.findRoleByName(roleName)
                .orElseThrow(() -> new UsernameNotFoundException
                (String.format("Role with roleName [%s] not found", roleName)));

        userEntity.setRole(roleEntity);

        userRepository.save(userEntity);
    }

    private UserResponse userEntityToUserResponse(UserEntity entity) {

        var userResponse = new UserResponse();

        userResponse.setUsername(entity.getUsername());

        if (entity.getRole() == null)
            return userResponse;

        userResponse.setRole(entity.getRole().getRoleName());

        return userResponse;
    }
}
