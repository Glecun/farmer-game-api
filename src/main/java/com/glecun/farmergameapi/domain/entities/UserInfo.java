package com.glecun.farmergameapi.domain.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserInfo {
    public final String id;
    public final String email;
    public final double money;
    public final double profit;
    public final List<HarvestableZone> harvestableZones;

    public UserInfo(String id, String email, double money, double profit, List<HarvestableZone> harvestableZones) {
        this.id = id;
        this.email = email;
        this.money = money;
        this.profit = profit;
        this.harvestableZones = harvestableZones;
    }

    public static UserInfo createUserInfo(String email) {
        List<HarvestableZone> harvestableZones = Arrays.stream(HarvestableZoneType.values())
                .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null))
                .collect(Collectors.toList());
        return new UserInfo(null, email, 200, 0, harvestableZones);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Double.compare(userInfo.money, money) == 0 && Double.compare(userInfo.profit, profit) == 0 && Objects.equals(id, userInfo.id) && Objects.equals(email, userInfo.email) && Objects.equals(harvestableZones, userInfo.harvestableZones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, money, profit, harvestableZones);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
              "id='" + id + '\'' +
              ", email='" + email + '\'' +
              ", money=" + money +
              ", profit=" + profit +
              ", harvestableZones=" + harvestableZones +
              '}';
    }

    public UserInfo replaceInHarvestableZones(HarvestableZone harvestableZoneToReplace) {
        List<HarvestableZone> newHarvestablesZones = harvestableZones.stream().map(harvestableZone -> {
            if (harvestableZone.hasType(harvestableZoneToReplace.harvestableZoneType)) {
                return harvestableZoneToReplace;
            }
            return harvestableZone;
        }).collect(Collectors.toList());
        return new UserInfo(id, email, money, profit, newHarvestablesZones);
    }

    public UserInfo DeduceMoney(Integer amountMoney) {
        return new UserInfo(id, email, money - amountMoney, profit, harvestableZones);
    }

    public UserInfo AddMoney(Integer amountMoney) {
        return new UserInfo(id, email, money + amountMoney, profit, harvestableZones);
    }

    public UserInfo AddProfit( int amount) {
        return new UserInfo(id, email, money, profit + amount, harvestableZones);
    }

    public boolean hasHarvestablePlantedWithSeedEnumAndInfoSaleEmpty(SeedEnum seedEnum) {
        return harvestableZones.stream()
                .map(HarvestableZone::getHarvestablePlanted)
                .flatMap(Optional::stream)
                .anyMatch(harvestablePlanted -> harvestablePlanted.seedsPlanted.seedEnum == seedEnum && harvestablePlanted.getInfoSale().isEmpty());
    }

    public UserInfo SetInfoSale(HarvestableZone harvestableZoneToUpdate, InfoSale infoSaleToReplace) {
        List<HarvestableZone> newHarvestablesZones = harvestableZones.stream().map(harvestableZone -> {
            if (harvestableZone.hasType(harvestableZoneToUpdate.harvestableZoneType)) {
                return new HarvestableZone(
                        harvestableZone.harvestableZoneType,
                        harvestableZone.getHarvestablePlanted().map(harvestablePlanted -> new HarvestablePlanted(harvestablePlanted.seedsPlanted, harvestablePlanted.whenPlanted, infoSaleToReplace)).orElseThrow()
                );
            }
            return harvestableZone;
        }).collect(Collectors.toList());

        return new UserInfo(id, email, money, profit, newHarvestablesZones);
    }

    public boolean hasStillHarvestableSold(SeedEnum seedEnum) {
        return harvestableZones.stream()
                .map(HarvestableZone::getHarvestablePlanted)
                .flatMap(Optional::stream)
                .map(HarvestablePlanted::getInfoSale)
                .flatMap(Optional::stream)
                .anyMatch(infoSale -> infoSale.nbHarvestableSold > 0);
    }
}
