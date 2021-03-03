package com.glecun.farmergameapi.infrastructure.repo;

import com.glecun.farmergameapi.infrastructure.dto.MarketInfoMongo;
import com.glecun.farmergameapi.infrastructure.dto.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MarketInfoRepository extends MongoRepository<MarketInfoMongo, String> {
}
