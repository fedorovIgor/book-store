package com.example.authorize.user.controller;

import com.example.authorize.user.entity.UserRequest;
import com.example.authorize.user.entity.UserResponse;
import com.example.authorize.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public Principal getPrincipal(Principal user) {
        return user;
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUser();
    }

    @GetMapping("users/roles")
    public List<String> getAllRoles() {
        return userService.getRoles();
    }

    @PutMapping("users/role")
    public void userInfo(@RequestBody UserRequest user) {
        System.out.println(user != null);
        var username = user.getUsername();
        var rolename = user.getRole();
        userService.changeUserRole(username, rolename);
    }

}
