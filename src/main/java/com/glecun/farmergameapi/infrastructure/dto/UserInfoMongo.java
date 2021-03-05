package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.UserInfo;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.stream.Collectors;

public class UserInfoMongo {

    @Id
    public String id;
    public final String email;
    public final double money;
    public final List<HarvestableZoneMongo> harvestableZones;

    public UserInfoMongo(String email, double money, List<HarvestableZoneMongo> harvestableZones) {
        this.email = email;
        this.money = money;
        this.harvestableZones = harvestableZones;
    }

    public static UserInfoMongo from(UserInfo userInfo) {
        return new UserInfoMongo(
              userInfo.email,
              userInfo.money,
              userInfo.harvestableZones.stream().map(HarvestableZoneMongo::from).collect(Collectors.toList())
        );
    }

    public UserInfo toUserInfo() {
        return new UserInfo(
              email,
              money,
              harvestableZones.stream().map(HarvestableZoneMongo::toHarvestableZone).collect(Collectors.toList())
        );
    }
}
