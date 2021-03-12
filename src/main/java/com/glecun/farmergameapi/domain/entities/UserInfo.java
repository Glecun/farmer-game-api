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
                .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null, harvestableZoneType.lockedByDefault))
                .collect(Collectors.toList());
        return new UserInfo(null, email, 200, 0, harvestableZones);
    }

    public double getProfit() {
        return profit;
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

    public UserInfo modifyMoney(Integer amountMoney) {
        double newMoney = this.money + amountMoney;
        if (newMoney < 0) {
            throw new RuntimeException("Cannot have negative money");
        }
        if (newMoney < 100) {
            newMoney = 100;
        }
        return new UserInfo(id, email, newMoney, profit, harvestableZones);
    }

    public UserInfo addProfit(int amount) {
        return new UserInfo(id, email, money, profit + amount, harvestableZones);
    }

    public boolean hasHarvestablePlantedWithSeedEnumAndInfoSaleEmptyAndOldOnSaleDate(SeedEnum seedEnum) {
        return harvestableZones.stream()
                .map(HarvestableZone::getHarvestablePlanted)
                .flatMap(Optional::stream)
                .anyMatch(harvestablePlanted -> harvestablePlanted.seedsPlanted.seedEnum == seedEnum &&
                        harvestablePlanted.getInfoSale().isEmpty() &&
                        harvestablePlanted.seedsPlanted.canBeSell()
                );
    }

    public UserInfo SetInfoSale(HarvestableZone harvestableZoneToUpdate, InfoSale infoSaleToReplace) {
        List<HarvestableZone> newHarvestablesZones = harvestableZones.stream().map(harvestableZone -> {
            if (harvestableZone.hasType(harvestableZoneToUpdate.harvestableZoneType)) {
                return new HarvestableZone(
                        harvestableZone.harvestableZoneType,
                        harvestableZone.getHarvestablePlanted().map(harvestablePlanted -> new HarvestablePlanted(harvestablePlanted.seedsPlanted, harvestablePlanted.whenPlanted, infoSaleToReplace)).orElseThrow(),
                        harvestableZone.isLocked
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

    public UserInfo unlockHarvestableZone(HarvestableZoneType harvestableZoneType) {
        List<HarvestableZone> newHarvestablesZones = harvestableZones.stream().map(harvestableZone -> {
            if (harvestableZone.hasType(harvestableZoneType)) {
                if(!harvestableZone.isLocked) {
                    throw new RuntimeException("zone already unlocked");
                }
                return new HarvestableZone(harvestableZoneType, harvestableZone.getHarvestablePlanted().orElse(null), false);
            }
            return harvestableZone;
        }).collect(Collectors.toList());
        return new UserInfo(id, email, money, profit, newHarvestablesZones);
    }

    public int getMaxNbOfZoneCapacity() {
        return harvestableZones.stream()
              .filter(harvestableZone -> !harvestableZone.isLocked)
              .map(harvestableZone -> harvestableZone.harvestableZoneType.nbOfZone)
              .collect(Collectors.toList()).stream().reduce(0, Integer::sum);
    }
}
