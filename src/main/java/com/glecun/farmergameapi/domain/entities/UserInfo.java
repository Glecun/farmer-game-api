package com.glecun.farmergameapi.domain.entities;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class UserInfo {
    public final String id;
    public final String email;
    public final double money;
    public final double profit;
    public final List<HarvestableZone> harvestableZones;
    public final List<SeedEnum> unlockedSeeds;

    public UserInfo(String id, String email, double money, double profit, List<HarvestableZone> harvestableZones, List<SeedEnum> unlockedSeeds) {
        this.id = id;
        this.email = email;
        this.money = money;
        this.profit = profit;
        this.harvestableZones = harvestableZones;
        this.unlockedSeeds = unlockedSeeds;
    }

    public static UserInfo createUserInfo(String email) {
        List<HarvestableZone> harvestableZones = Arrays.stream(HarvestableZoneType.values())
                .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null, harvestableZoneType.lockedByDefault))
                .collect(Collectors.toList());
        return new UserInfo(null, email, 200, 0, harvestableZones, Collections.emptyList());
    }

    public double getProfit() {
        return profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Double.compare(userInfo.money, money) == 0 &&
                Double.compare(userInfo.profit, profit) == 0 &&
                Objects.equals(id, userInfo.id) &&
                Objects.equals(email, userInfo.email) &&
                Objects.equals(harvestableZones, userInfo.harvestableZones) &&
                Objects.equals(unlockedSeeds, userInfo.unlockedSeeds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, money, profit, harvestableZones, unlockedSeeds);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserInfo{");
        sb.append("id='").append(id).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", money=").append(money);
        sb.append(", profit=").append(profit);
        sb.append(", harvestableZones=").append(harvestableZones);
        sb.append(", unlockedSeeds=").append(unlockedSeeds);
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
        return new UserInfo(id, email, money, profit, newHarvestablesZones, unlockedSeeds);
    }

    public UserInfo modifyMoney(Integer amountMoney) {
        double newMoney = this.money + amountMoney;
        if (newMoney < 0) {
            throw new RuntimeException("Cannot have negative money");
        }
        if (newMoney < 100) {
            newMoney = 100;
        }
        return new UserInfo(id, email, newMoney, profit, harvestableZones, unlockedSeeds);
    }

    public UserInfo addProfit(int amount) {
        return new UserInfo(id, email, money, profit + amount, harvestableZones, unlockedSeeds);
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

        return new UserInfo(id, email, money, profit, newHarvestablesZones, unlockedSeeds);
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
        return new UserInfo(id, email, money, profit, newHarvestablesZones, unlockedSeeds);
    }

    public int getMaxNbOfZoneCapacity() {
        return harvestableZones.stream()
              .filter(harvestableZone -> !harvestableZone.isLocked)
              .map(harvestableZone -> harvestableZone.harvestableZoneType.nbOfZone)
              .collect(Collectors.toList()).stream().reduce(0, Integer::sum);
    }

    public HarvestableZone getHarvestableZone(HarvestableZoneType harvestableZoneType) {
        return harvestableZones.stream()
                .filter(aHarvestableZone -> aHarvestableZone.harvestableZoneType == harvestableZoneType)
                .findFirst().orElseThrow();
    }

    public UserInfo plant(HarvestableZoneType harvestableZoneType, OnSaleSeed onSaleSeed, Supplier<LocalDateTime> now) {
        if (!hasUnlockedSeed(onSaleSeed.seedEnum)){
            throw new RuntimeException("Seed locked");
        }
        return replaceInHarvestableZones(getHarvestableZone(harvestableZoneType).plant(onSaleSeed, now));
    }

    public UserInfo unlockSeed(SeedEnum seedEnum) {
        if(hasUnlockedSeed(seedEnum)) {
            throw new RuntimeException("Already have this seed");
        }
        var newUnlockedSeeds = new ArrayList<>(unlockedSeeds);
        newUnlockedSeeds.add(seedEnum);
        return new UserInfo(id, email, money, profit, harvestableZones, newUnlockedSeeds);
    }

    public boolean hasUnlockedSeed(SeedEnum seedEnum) {
        return unlockedSeeds.stream().anyMatch(aSeedEnum -> aSeedEnum.equals(seedEnum));
    }
}
