package com.qsp.roleAccessed.userDe;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.qsp.roleAccessed.entity.User;

public class UserInfoFetch implements UserDetails {

    private User user;

    public UserInfoFetch(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // 🔥 Role DB se aayega (example: ROLE_ADMIN / ROLE_USER)
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();   // ✅ DB password
    }

    @Override
    public String getUsername() {
        return user.getEmail();   // ✅ login email
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