package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class UserMongo {
    @Id
    public String id;
    public String username;
    public String email;
    public String password;

    public UserMongo(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static UserMongo from(User user) {
        return new UserMongo(user.getUsername(), user.getEmail(), user.getPassword());
    }

    public static User toUser(UserMongo userMongo) {
        return new User(userMongo.username, userMongo.email, userMongo.password);
    }
}
