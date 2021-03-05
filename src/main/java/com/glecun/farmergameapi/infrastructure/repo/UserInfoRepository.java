package com.glecun.farmergameapi.infrastructure.repo;

import com.glecun.farmergameapi.infrastructure.dto.MarketInfoMongo;
import com.glecun.farmergameapi.infrastructure.dto.UserInfoMongo;
import com.glecun.farmergameapi.infrastructure.dto.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserInfoRepository extends MongoRepository<UserInfoMongo, String> {
    Optional<UserInfoMongo> findByEmail(String email);
}
