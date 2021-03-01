package com.glecun.farmergameapi.infrastructure.repo;

import com.glecun.farmergameapi.domain.entities.User;
import com.glecun.farmergameapi.infrastructure.dto.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserMongoRepository extends MongoRepository<UserMongo, String> {

    Optional<UserMongo> findByEmail(String email);
}
