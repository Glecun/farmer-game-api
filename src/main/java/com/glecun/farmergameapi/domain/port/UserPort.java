package com.glecun.farmergameapi.domain.port;

import com.glecun.farmergameapi.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserPort {
    Optional<User> findByEmail(String email);

    void save(User user);

    List<User> findAll();
}
