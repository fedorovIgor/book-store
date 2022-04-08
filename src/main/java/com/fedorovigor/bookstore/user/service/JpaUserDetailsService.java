package com.fedorovigor.bookstore.user.service;

import com.fedorovigor.bookstore.user.repository.UserRepository;
import com.fedorovigor.bookstore.user.model.dto.Authority;
import com.fedorovigor.bookstore.user.model.dto.Role;
import com.fedorovigor.bookstore.user.model.dto.User;
import com.fedorovigor.bookstore.user.model.entity.AuthorityEntity;
import com.fedorovigor.bookstore.user.model.entity.UserEntity;
import com.fedorovigor.bookstore.user.model.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsManager {

    private final PasswordEncoder passwordEncoder;
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

        user.setUsername(entity.getUsername());
        user.setPassword(entity.getPassword());

        if (entity.getRole() == null)
            return user;

        var roleEntity = entity.getRole();
        Collection<AuthorityEntity> authoritiesEntity = roleEntity.getAuthorities();

        var role = new Role();
        role.setRoleName(roleEntity.getRoleName());
        role.setAuthorities(
                authoritiesEntity.stream()
                        .map(a -> new Authority(a.getName(), a.getDescription()))
                        .collect(Collectors.toList())
        );

        user.setRole(role);

        return user;
    }



    @Override
    public void createUser(UserDetails user) {
        validateUserDetails(user);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userEntity);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void updateUser(UserDetails user) {
        validateUserDetails(user);

        var username = user.getUsername();

        if (userExists(username))
            throw new RuntimeException(String
                    .format("user with name [%s] already exist", username));

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(String username) {
        throw new RuntimeException("method has`t implemented");
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        validatePassword(newPassword);

        Authentication currentUser = SecurityContextHolder.getContext()
                .getAuthentication();

        if (currentUser == null) {
            throw new AccessDeniedException(
                    "Can't change password as no Authentication object found in context "
                            + "for current user.");
        }

        UserDetails userDetails = (UserDetails) currentUser.getPrincipal();

        if (!passwordEncoder.matches(oldPassword, userDetails.getPassword()))
            throw new RuntimeException("given currant password incorrect " + oldPassword);

        String username = userDetails.getUsername();

        userRepository.updatePassword(passwordEncoder.encode(newPassword), username);

        SecurityContextHolder.getContext().setAuthentication(
                createNewAuthentication(currentUser, passwordEncoder.encode(newPassword)));

    }

    private Authentication createNewAuthentication(Authentication currentAuth,
                                                     String newPassword) {
        UserDetails user = loadUserByUsername(currentAuth.getName());

        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());

        return newAuthentication;
    }


    @Override
    public boolean userExists(String username) {
        return userRepository.isUserExistByUsername(username);
    }


    private void validateUserDetails(UserDetails user) {
        if (user.getUsername() == null || user.getUsername().strip() == "")
            throw new RuntimeException("Username may not be empty or null");

        validatePassword(user.getPassword());
    }

    private void validatePassword(String password) {
        if (password == null || password.strip() == "")
            throw new RuntimeException("Password may not be empty or null");
    }
}