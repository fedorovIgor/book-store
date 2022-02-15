package com.example.authorize.user.controller;

import com.example.authorize.user.entity.Role;
import com.example.authorize.user.entity.UserRequest;
import com.example.authorize.user.entity.UserResponse;
import com.example.authorize.user.service.RoleService;
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
    private final RoleService roleService;

    @GetMapping("/login")
    public Principal getPrincipal(Principal user) {
        return user;
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {

        roleService.getAllRolesWithAuthorities();
        return userService.getAllUser();
    }


    @PutMapping("/users/role")
    public void userInfo(@RequestBody UserRequest user) {
        var username = user.getUsername();
        var rolename = user.getRole();
        userService.changeUserRole(username, rolename);
    }

}
