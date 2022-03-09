package com.example.authorize.user.model;

import com.example.authorize.user.model.dto.Authority;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import static java.util.Arrays.stream;

@RequiredArgsConstructor
@ToString
public class GrantedAuthorityRealization implements GrantedAuthority {

    private final Authority authority;

    @Override
    public String getAuthority() {
        return authority.getName();
    }
}
