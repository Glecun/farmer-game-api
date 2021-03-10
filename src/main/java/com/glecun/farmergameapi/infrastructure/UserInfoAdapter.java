package com.glecun.farmergameapi.infrastructure;

import com.glecun.farmergameapi.domain.entities.UserInfo;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import com.glecun.farmergameapi.infrastructure.dto.UserInfoMongo;
import com.glecun.farmergameapi.infrastructure.repo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<UserInfo> findAll() {
        return userInfoRepository.findAll().stream().map(UserInfoMongo::toUserInfo).collect(Collectors.toList());
    }

    @Override
    public void saveAll(List<UserInfo> userInfos) {
        userInfoRepository.saveAll(userInfos.stream().map(UserInfoMongo::from).collect(Collectors.toList()));

    }

    @Override
    public long countAll() {
        return userInfoRepository.count();

    }
}
