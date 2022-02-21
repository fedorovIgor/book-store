package com.example.authorize.user.controller;

import com.example.authorize.user.entity.SecurityUser;
import com.example.authorize.user.entity.User;
import com.example.authorize.user.entity.UserRequest;
import com.example.authorize.user.entity.UserResponse;
import com.example.authorize.user.service.JpaUserDetailsService;
import com.example.authorize.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JpaUserDetailsService userDetailsService;

    @GetMapping("/login")
    public Principal getPrincipal(Principal user) {
        return user;
    }

    @GetMapping("/name")
    public String getMyName(Authentication a) {
        return a.getName();
    }

    @PutMapping("/password")
    public void changePassword(@RequestBody UserRequest request) {
        userDetailsService.changePassword(
                request.getOldPassword(),
                request.getNewPassword());
    }

    @PutMapping("/username")
    public void updateUser(@RequestBody UserRequest request) {
        userDetailsService.updateUser(new SecurityUser(new User(
                request.getUsername(), request.getOldPassword(), null
        )));
    }

    @PostMapping("/create")
    public void createNewUser(@RequestBody UserRequest request) {
        userDetailsService.createUser(new SecurityUser(new User(
                request.getUsername(), request.getNewPassword(), null
        )));
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUser();
    }

    @PutMapping("/users/role") public void userInfo(@RequestBody UserRequest user) {
        var username = user.getUsername();
        var roleName = user.getRole();
        userService.changeUserRole(username, roleName);
    }

}
