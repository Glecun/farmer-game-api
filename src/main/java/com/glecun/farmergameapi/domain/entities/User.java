package com.glecun.farmergameapi.domain.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class User implements UserDetails {

    private final String username;
    private final String email;
    private final String password;
    private final UserRole userRole = UserRole.USER;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
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

    public User setPassword(String encryptedPassword) {
        return new User(username,email,encryptedPassword);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getEmail(), user.getEmail()) && userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getEmail(), userRole);
    }

    @Override
    public String toString() {
        return "User{" +
              "username='" + username + '\'' +
              ", email='" + email + '\'' +
              ", password='" + password + '\'' +
              ", userRole=" + userRole +
              '}';
    }
}
