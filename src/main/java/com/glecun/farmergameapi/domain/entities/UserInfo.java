package com.glecun.farmergameapi.domain.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserInfo {
    public final String email;
    public final double money;
    public final List<HarvestableZone> harvestableZones;

    public UserInfo(String email, double money, List<HarvestableZone> harvestableZones) {
        this.email = email;
        this.money = money;
        this.harvestableZones = harvestableZones;
    }

    public static UserInfo createUserInfo(String email) {
        List<HarvestableZone> harvestableZones = Arrays.stream(HarvestableZoneType.values())
                .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null))
                .collect(Collectors.toList());
        return new UserInfo(email, 200, harvestableZones);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Double.compare(userInfo.money, money) == 0 && Objects.equals(email, userInfo.email) && Objects.equals(harvestableZones, userInfo.harvestableZones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, money, harvestableZones);
    }
}
