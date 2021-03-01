package com.glecun.farmergameapi.infrastructure;

import com.glecun.farmergameapi.domain.entities.User;
import com.glecun.farmergameapi.domain.port.UserPort;
import com.glecun.farmergameapi.infrastructure.dto.UserMongo;
import com.glecun.farmergameapi.infrastructure.repo.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserAdapter implements UserPort {

    private final UserMongoRepository userMongoRepository;

    @Autowired
    public UserAdapter(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userMongoRepository.findByEmail(email).map(UserMongo::toUser);
    }

    public void save(User user) {
        userMongoRepository.save(UserMongo.from(user));
    }
}
