package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.TierEnum;
import com.glecun.farmergameapi.domain.entities.UserInfo;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoMongo {

    @Id
    public String id;
    public final String email;
    public final double money;
    public final ProfitsByTiersMongo profitsByTiers;
    public final List<HarvestableZoneMongo> harvestableZones;
    public final List<TierEnum> unlockedTiers;
    public final LocalDateTime lastTimePlant;

    public UserInfoMongo(String id, String email, double money, ProfitsByTiersMongo profitsByTiers, List<HarvestableZoneMongo> harvestableZones, List<TierEnum> unlockedTiers, LocalDateTime lastTimePlant) {
        this.id = id;
        this.email = email;
        this.money = money;
        this.profitsByTiers = profitsByTiers;
        this.harvestableZones = harvestableZones;
        this.unlockedTiers = unlockedTiers;
        this.lastTimePlant = lastTimePlant;
    }

    public static UserInfoMongo from(UserInfo userInfo) {
        return new UserInfoMongo(
              userInfo.id,
              userInfo.email,
              userInfo.money,
              ProfitsByTiersMongo.from(userInfo.profits),
              userInfo.harvestableZones.stream().map(HarvestableZoneMongo::from).collect(Collectors.toList()),
              userInfo.unlockedTiers,
              userInfo.lastTimePlant
        );
    }

    public UserInfo toUserInfo() {
        return new UserInfo(
              id,
              email,
              money,
              profitsByTiers.toProfitsByTiers(),
              harvestableZones.stream().map(HarvestableZoneMongo::toHarvestableZone).collect(Collectors.toList()),
              unlockedTiers,
              lastTimePlant
        );
    }
}
