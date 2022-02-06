package com.example.authorize.user.entity;

import com.example.authorize.user.entity.GrantedAuthorityRealization;
import com.example.authorize.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
@ToString
public class SecurityUser implements UserDetails {

    private final User user;
    private final List<GrantedAuthorityRealization> authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
