package com.zmc.article.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author: Demo
 * @date: 2021-09-22 17:22
 */
public class UserCommonDetail implements UserDetails {

    private long id;

    private String password;

    private String username;

    private Set<GrantedAuthority> authorities;

    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public UserCommonDetail setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public long getId() {
        return id;
    }

    public UserCommonDetail setId(long id) {
        this.id = id;
        return this;
    }

    public UserCommonDetail setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserCommonDetail setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserCommonDetail setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }
}
