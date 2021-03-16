package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.SeedEnum;
import com.glecun.farmergameapi.domain.entities.TierEnum;
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
    public final List<TierEnum> unlockedTiers;

    public UserInfoMongo(String id, String email, double money, double profit, List<HarvestableZoneMongo> harvestableZones, List<TierEnum> unlockedTiers) {
        this.id = id;
        this.email = email;
        this.money = money;
        this.profit = profit;
        this.harvestableZones = harvestableZones;
        this.unlockedTiers = unlockedTiers;
    }

    public static UserInfoMongo from(UserInfo userInfo) {
        return new UserInfoMongo(
              userInfo.id,
              userInfo.email,
              userInfo.money,
              userInfo.profit,
              userInfo.harvestableZones.stream().map(HarvestableZoneMongo::from).collect(Collectors.toList()),
              userInfo.unlockedTiers
        );
    }

    public UserInfo toUserInfo() {
        return new UserInfo(
              id,
              email,
              money,
              profit,
              harvestableZones.stream().map(HarvestableZoneMongo::toHarvestableZone).collect(Collectors.toList()),
              unlockedTiers
        );
    }
}
