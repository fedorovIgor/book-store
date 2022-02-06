package com.example.authorize.user.entity;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
@ToString
public class GrantedAuthorityRealization implements GrantedAuthority {

    private final Role role;

    @Override
    public String getAuthority() {
        return role.getRoleName();
    }
}
