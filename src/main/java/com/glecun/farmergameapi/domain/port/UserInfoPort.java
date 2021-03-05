package com.glecun.farmergameapi.domain.port;
import com.glecun.farmergameapi.domain.entities.UserInfo;
import com.glecun.farmergameapi.infrastructure.dto.UserInfoMongo;

import java.util.Optional;


public interface UserInfoPort {
    Optional<UserInfo> findByEmail(String email);

    UserInfo save(UserInfo userInfo);
}
