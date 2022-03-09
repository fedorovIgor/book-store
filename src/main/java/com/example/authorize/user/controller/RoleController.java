package com.example.authorize.user.controller;

import com.example.authorize.user.model.dto.Role;
import com.example.authorize.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<Role> getRolesWithAuthority() {
        return roleService.getAllRolesWithAuthorities();
    }

    @GetMapping("/{roleName}")
    public Role getRoleByName(@PathVariable String roleName) {
        return roleService.findRoleByName(roleName);
    }


    @GetMapping("/names")
    public List<String> getAllRoleNames() {
        return roleService.getRoleNames();
    }


    @PostMapping
    public void createNewRole(@RequestBody Role role) {
        roleService.createNewRole(role);
    }


    @PutMapping
    public void changeRole(@RequestBody Role role) {
        roleService.changeRole(role);
    }
}
