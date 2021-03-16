package com.glecun.farmergameapi.application.dto;
import com.glecun.farmergameapi.domain.entities.SeedEnum;
import com.glecun.farmergameapi.domain.entities.TierEnum;
import com.glecun.farmergameapi.domain.entities.UserInfo;

import java.util.List;
import java.util.stream.Collectors;

public class UserInfoJson {
    public final String email;
    public final double money;
    public final List<HarvestableZoneJson> harvestableZones;
    public final List<TierEnum> unlockedTiers;

    public UserInfoJson(String email, double money, List<HarvestableZoneJson> harvestableZones, List<TierEnum> unlockedTiers) {
        this.email = email;
        this.money = money;
        this.harvestableZones = harvestableZones;
        this.unlockedTiers = unlockedTiers;
    }

    public static UserInfoJson from(UserInfo userInfo) {
        return new UserInfoJson(
                userInfo.email,
                userInfo.money,
                userInfo.harvestableZones.stream().map(HarvestableZoneJson::from).collect(Collectors.toList()),
                userInfo.unlockedTiers
        );
    }
}
