package com.fedorovigor.bookstore.user.model;

import com.fedorovigor.bookstore.user.model.dto.Authority;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
@ToString
public class GrantedAuthorityRealization implements GrantedAuthority {

    private final Authority authority;

    @Override
    public String getAuthority() {
        return authority.getName();
    }
}
