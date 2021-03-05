package com.glecun.farmergameapi.domain.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserInfo {
    public final String id;
    public final String email;
    public final double money;
    public final List<HarvestableZone> harvestableZones;

    public UserInfo(String id, String email, double money, List<HarvestableZone> harvestableZones) {
        this.id = id;
        this.email = email;
        this.money = money;
        this.harvestableZones = harvestableZones;
    }

    public static UserInfo createUserInfo(String email) {
        List<HarvestableZone> harvestableZones = Arrays.stream(HarvestableZoneType.values())
                .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null))
                .collect(Collectors.toList());
        return new UserInfo(null, email, 200, harvestableZones);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Double.compare(userInfo.money, money) == 0 &&
                Objects.equals(id, userInfo.id) &&
                Objects.equals(email, userInfo.email) &&
                Objects.equals(harvestableZones, userInfo.harvestableZones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, money, harvestableZones);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserInfo{");
        sb.append("id='").append(id).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", money=").append(money);
        sb.append(", harvestableZones=").append(harvestableZones);
        sb.append('}');
        return sb.toString();
    }

    public UserInfo replaceInHarvestableZones(HarvestableZone harvestableZoneToReplace) {
        List<HarvestableZone> newHarvestablesZones = harvestableZones.stream().map(harvestableZone -> {
            if (harvestableZone.hasType(harvestableZoneToReplace.harvestableZoneType)) {
                return harvestableZoneToReplace;
            }
            return harvestableZone;
        }).collect(Collectors.toList());
        return new UserInfo(id, email,money,newHarvestablesZones);
    }

    public UserInfo DeduceMoney(Integer amountMoney) {
        return new UserInfo(id, email, money - amountMoney, harvestableZones);
    }
}
