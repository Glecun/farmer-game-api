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
    public final double profit;
    public final List<HarvestableZoneMongo> harvestableZones;

    public UserInfoMongo(String id, String email, double money, double profit, List<HarvestableZoneMongo> harvestableZones) {
        this.id = id;
        this.email = email;
        this.money = money;
        this.profit = profit;
        this.harvestableZones = harvestableZones;
    }

    public static UserInfoMongo from(UserInfo userInfo) {
        return new UserInfoMongo(
              userInfo.id,
              userInfo.email,
              userInfo.money,
              userInfo.profit,
              userInfo.harvestableZones.stream().map(HarvestableZoneMongo::from).collect(Collectors.toList())
        );
    }

    public UserInfo toUserInfo() {
        return new UserInfo(
              id,
              email,
              money,
              profit,
              harvestableZones.stream().map(HarvestableZoneMongo::toHarvestableZone).collect(Collectors.toList())
        );
    }
}
