package com.glecun.farmergameapi.infrastructure;

import com.glecun.farmergameapi.domain.entities.UserInfo;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import com.glecun.farmergameapi.infrastructure.dto.UserInfoMongo;
import com.glecun.farmergameapi.infrastructure.repo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserInfoAdapter implements UserInfoPort {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoAdapter(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public Optional<UserInfo> findByEmail(String email) {
        return userInfoRepository.findByEmail(email).map(UserInfoMongo::toUserInfo);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(UserInfoMongo.from(userInfo)).toUserInfo();
    }
}
