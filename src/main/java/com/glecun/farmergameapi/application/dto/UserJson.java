package com.glecun.farmergameapi.application.dto;

import com.glecun.farmergameapi.domain.entities.User;
import com.glecun.farmergameapi.domain.entities.UserRole;

public class UserJson {

   private String username;
   private String email;
   private String password;

    public UserJson(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User toUser() {
        return new User(username,email,password);
    }
}
