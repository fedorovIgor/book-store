package com.example.authorize.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class UserResponse {

    private final User user;
    private final Role role;

    public String getUsername() {
        return this.user.getUsername();
    }

    public String getRoleName() {
        return this.role.getRoleName();
    }

}
