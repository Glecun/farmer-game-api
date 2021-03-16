package com.glecun.farmergameapi.domain.entities;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.glecun.farmergameapi.domain.entities.TierEnum.*;

public class UserInfo {
    public final String id;
    public final String email;
    public final double money;
    public final double profit;
    public final List<HarvestableZone> harvestableZones;
    public final List<TierEnum> unlockedTiers;

    public UserInfo(String id, String email, double money, double profit, List<HarvestableZone> harvestableZones, List<TierEnum> unlockedTiers) {
        this.id = id;
        this.email = email;
        this.money = money;
        this.profit = profit;
        this.harvestableZones = harvestableZones;
        this.unlockedTiers = unlockedTiers;
    }

    public static UserInfo createUserInfo(String email) {
        List<HarvestableZone> harvestableZones = Arrays.stream(HarvestableZoneType.values())
                .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null, harvestableZoneType.lockedByDefault))
                .collect(Collectors.toList());
        return new UserInfo(null, email, 180, 0, harvestableZones, Collections.singletonList(TIER_1));
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
                Objects.equals(unlockedTiers, userInfo.unlockedTiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, money, profit, harvestableZones, unlockedTiers);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserInfo{");
        sb.append("id='").append(id).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", money=").append(money);
        sb.append(", profit=").append(profit);
        sb.append(", harvestableZones=").append(harvestableZones);
        sb.append(", unlockedTiers=").append(unlockedTiers);
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
        return new UserInfo(id, email, money, profit, newHarvestablesZones, unlockedTiers);
    }

    public UserInfo modifyMoney(Integer amountMoney) {
        double newMoney = this.money + amountMoney;
        if (newMoney < 0) {
            throw new RuntimeException("Cannot have negative money");
        }
        if (newMoney < 12) {
            newMoney = 12;
        }
        return new UserInfo(id, email, newMoney, profit, harvestableZones, unlockedTiers);
    }

    public UserInfo addProfit(int amount) {
        return new UserInfo(id, email, money, profit + amount, harvestableZones, unlockedTiers);
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

        return new UserInfo(id, email, money, profit, newHarvestablesZones, unlockedTiers);
    }

    public Integer getNbHarvestable(SeedEnum seedEnum) {
        return harvestableZones.stream()
                .map(HarvestableZone::getHarvestablePlanted)
                .flatMap(Optional::stream)
                .filter(harvestablePlanted -> harvestablePlanted.seedsPlanted.seedEnum == seedEnum)
                .map(HarvestablePlanted::getInfoSale)
                .flatMap(Optional::stream)
                .map(infoSale -> infoSale.nbHarvestableSold)
                .reduce(0, Integer::sum);
    }

    public UserInfo unlockHarvestableZone(HarvestableZoneType harvestableZoneType) {
        List<HarvestableZone> newHarvestablesZones = harvestableZones.stream().map(harvestableZone -> {
            if (harvestableZone.hasType(harvestableZoneType)) {
                if(!harvestableZone.isLocked) {
                    throw new RuntimeException("Zone already unlocked");
                }
                if(!hasTierOfZoneUnlocked(harvestableZoneType)) {
                    throw new RuntimeException("Tier locked for this zone");
                }
                return new HarvestableZone(harvestableZoneType, harvestableZone.getHarvestablePlanted().orElse(null), false);
            }
            return harvestableZone;
        }).collect(Collectors.toList());
        return new UserInfo(id, email, money, profit, newHarvestablesZones, unlockedTiers);
    }

    public int getMaxNbOfZoneCapacityForThisSeed(SeedEnum seedEnum) {
        return harvestableZones.stream()
              .filter(harvestableZone -> hasUnlockedZone(harvestableZone.harvestableZoneType))
              .filter(harvestableZone -> isSeedAndZoneInSameTier(harvestableZone.harvestableZoneType, seedEnum))
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
        if (!hasUnlockedZone(harvestableZoneType)){
            throw new RuntimeException("Zone locked");
        }
        if (!isSeedAndZoneInSameTier(harvestableZoneType, onSaleSeed.seedEnum)){
            throw new RuntimeException("Zone and Seed has to be in same tier");
        }
        return replaceInHarvestableZones(getHarvestableZone(harvestableZoneType).plant(onSaleSeed, now));
    }

    public UserInfo unlockTier(TierEnum tierEnum) {
        if(hasUnlockedTier(tierEnum)) {
            throw new RuntimeException("Already have this tier unlocked");
        }
        var newUnlockedTiers = new ArrayList<>(unlockedTiers);
        newUnlockedTiers.add(tierEnum);
        return new UserInfo(id, email, money, profit, harvestableZones, newUnlockedTiers);
    }

    public boolean hasUnlockedSeed(SeedEnum seedEnum) {
        return unlockedTiers.stream().anyMatch(tierEnum -> tierEnum.seeds.contains(seedEnum));
    }

    public boolean hasUnlockedZone(HarvestableZoneType harvestableZoneType) {
        return  hasTierOfZoneUnlocked(harvestableZoneType)
                && !getHarvestableZone(harvestableZoneType).isLocked ;
    }

    private boolean hasTierOfZoneUnlocked(HarvestableZoneType harvestableZoneType) {
        return unlockedTiers.stream().anyMatch(tierEnum -> tierEnum.zones.contains(harvestableZoneType));
    }

    public boolean hasUnlockedTier(TierEnum tierEnum) {
        return unlockedTiers.stream().anyMatch(aTierEnum -> aTierEnum.equals(tierEnum));
    }
}
