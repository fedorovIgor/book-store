package com.fedorovigor.bookstore.user.controller;

import com.fedorovigor.bookstore.user.model.SecurityUser;
import com.fedorovigor.bookstore.user.model.dto.User;
import com.fedorovigor.bookstore.user.model.dto.UserRequest;
import com.fedorovigor.bookstore.user.model.dto.UserResponse;
import com.fedorovigor.bookstore.user.service.JpaUserDetailsService;
import com.fedorovigor.bookstore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JpaUserDetailsService userDetailsService;

    @GetMapping("/login")
    public Principal getPrincipal(Principal user) {
        return user;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUser();
    }

    @PostMapping("/create")
    public void createNewUser(@RequestBody UserRequest request) {
        userDetailsService.createUser(new SecurityUser(new User(
                request.getUsername(), request.getNewPassword(), null
        )));
    }

    @PutMapping("/password")
    public void changePassword(@RequestBody UserRequest request) {
        userDetailsService.changePassword(
                request.getOldPassword(),
                request.getNewPassword());
    }

    @PutMapping("/role") public void changeUserRole(@RequestBody UserRequest user) {
        userService.changeUserRole(
                user.getUsername(),
                user.getRole());
    }

}
