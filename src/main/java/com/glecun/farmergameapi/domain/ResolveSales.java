package com.glecun.farmergameapi.domain;

import com.glecun.farmergameapi.domain.entities.*;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ResolveSales {

    private final UserInfoPort userInfoPort;
    @Autowired
    public ResolveSales(UserInfoPort userInfoPort) {
        this.userInfoPort = userInfoPort;
    }

    public void execute(GrowthTime growthTime) {
        Arrays.stream(SeedEnum.values())
                .filter(seedEnum -> seedEnum.seed.hasGrowthTime(growthTime))
                .forEach(this::resolveForHarvestable);
    }

    private void resolveForHarvestable(SeedEnum seedEnum) {
        List<UserInfo> userConcerned = getUsersWhoPlantedAtLeastOneHarvestable(seedEnum);

        if (!userConcerned.isEmpty()) {
            OnSaleSeed onSaleSeedConcerned = getOnSaleSeedConcerned(seedEnum, userConcerned);
            int nbTotalHarvestable = getNbTotalHarvestable(seedEnum, userConcerned);
            int nbFarmer = userConcerned.size();

            List<UserInfo> userInfosToUpdate = userConcerned.stream()
                    .map(userInfo -> getUserInfoToSave(seedEnum, onSaleSeedConcerned, nbTotalHarvestable, nbFarmer, userInfo))
                    .collect(Collectors.toList());

            int nbHarvestableNotSold = nbTotalHarvestable - onSaleSeedConcerned.demand.nbDemand;
            userInfosToUpdate = maybeReduceSales(userInfosToUpdate, nbHarvestableNotSold, seedEnum, onSaleSeedConcerned);

            userInfoPort.saveAll(userInfosToUpdate);

        }
    }

    private static class HarvestableZoneToUpdate {
        public final HarvestableZone harvestableZone;
        public final InfoSale infoSale;

        public HarvestableZoneToUpdate(HarvestableZone harvestableZone, InfoSale infoSale) {
            this.harvestableZone = harvestableZone;
            this.infoSale = infoSale;
        }
    }
    private UserInfo getUserInfoToSave(SeedEnum seedEnum, OnSaleSeed onSaleSeedConcerned, int nbTotalHarvestable, int nbFarmer, UserInfo userInfo) {
        return userInfo.harvestableZones.stream()
                .map(harvestableZone -> harvestableZone.getHarvestablePlanted()
                        .filter(harvestablePlanted -> harvestablePlanted.seedsPlanted.seedEnum == seedEnum)
                        .filter(harvestablePlanted -> harvestablePlanted.getInfoSale().isEmpty())
                        .map(harvestablePlanted -> new HarvestableZoneToUpdate(
                                harvestableZone,
                                new InfoSale(
                                        nbFarmer,
                                        nbTotalHarvestable,
                                        harvestableZone.harvestableZoneType.nbOfZone,
                                        true,
                                        harvestableZone.harvestableZoneType.nbOfZone * onSaleSeedConcerned.sellPrice,
                                        (harvestableZone.harvestableZoneType.nbOfZone * onSaleSeedConcerned.sellPrice) - (harvestableZone.harvestableZoneType.nbOfZone * onSaleSeedConcerned.buyPrice)
                                )
                        )))
                .flatMap(Optional::stream)
                .reduce(
                    userInfo,
                    (userInfo1, harvestableZoneToUpdate) -> userInfo1.SetInfoSale(harvestableZoneToUpdate.harvestableZone, harvestableZoneToUpdate.infoSale),
                    (userInfo1, userInfo2) -> userInfo1
                );
    }

    private List<UserInfo> getUsersWhoPlantedAtLeastOneHarvestable(SeedEnum seedEnum) {
        return userInfoPort.findAll().stream()
                .filter(userInfo -> userInfo.hasHarvestablePlantedWithSeedEnumAndInfoSaleEmpty(seedEnum))
                .collect(Collectors.toList());
    }

    private int getNbTotalHarvestable(SeedEnum seedEnum, List<UserInfo> userConcerned) {
        return userConcerned.stream()
                .map(userInfo -> userInfo.harvestableZones).flatMap(List::stream)
                .map(harvestableZone -> harvestableZone.getHarvestablePlanted()
                        .filter(harvestablePlanted -> harvestablePlanted.seedsPlanted.seedEnum == seedEnum && harvestablePlanted.getInfoSale().isEmpty())
                        .map(harvestablePlanted -> harvestableZone.harvestableZoneType.nbOfZone)
                        .orElse(0))
                .reduce(0, Integer::sum);
    }

    private OnSaleSeed getOnSaleSeedConcerned(SeedEnum seedEnum, List<UserInfo> userConcerned) {
        return userConcerned.stream().findAny()
                .map(userInfo -> userInfo.harvestableZones).stream().flatMap(List::stream)
                .map(HarvestableZone::getHarvestablePlanted).flatMap(Optional::stream)
                .filter(harvestablePlanted -> harvestablePlanted.seedsPlanted.seedEnum == seedEnum)
                .findFirst()
                .map(harvestablePlanted -> harvestablePlanted.seedsPlanted)
                .orElseThrow();
    }

    private List<UserInfo> maybeReduceSales(List<UserInfo> userInfosToUpdate, int nbHarvestableNotSold, SeedEnum seedEnum, OnSaleSeed onSaleSeedConcerned) {
        List<UserInfo> updatedUserInfosToUpdate = new ArrayList<>(userInfosToUpdate);
        for (int i = 0 ; i < nbHarvestableNotSold ; i++) {
            UserInfo randomUserInfo;
            do {
                randomUserInfo = updatedUserInfosToUpdate.stream().skip((int) (userInfosToUpdate.size() * Math.random())).findAny().orElseThrow();
            } while (!randomUserInfo.hasStillHarvestableSold(seedEnum));

            var harvestableZoneToUpdate =  randomUserInfo.harvestableZones.stream()
                    .map(harvestableZone ->
                            harvestableZone.getHarvestablePlanted()
                                    .filter(harvestablePlanted -> harvestablePlanted.seedsPlanted.seedEnum == seedEnum)
                                    .filter(harvestablePlanted -> harvestablePlanted.getInfoSale().map(infoSale -> infoSale.nbHarvestableSold > 0).orElse(false))
                                    .stream().findAny()
                                    .map(harvestablePlanted -> {
                                        InfoSale infoSale = harvestableZone.getHarvestablePlanted().map(HarvestablePlanted::getInfoSale).flatMap(Function.identity()).orElseThrow();
                                        return new HarvestableZoneToUpdate(
                                                harvestableZone,
                                                new InfoSale(
                                                        infoSale.nbFarmer,
                                                        infoSale.nbTotalHarvestableSold,
                                                        infoSale.nbHarvestableSold - 1,
                                                        false,
                                                        infoSale.revenue - onSaleSeedConcerned.sellPrice,
                                                        infoSale.profit - onSaleSeedConcerned.sellPrice
                                                )
                                        );
                                    })
                    )
                    .flatMap(Optional::stream)
                    .findAny()
                    .orElseThrow();
            var userInfoToUpdate = randomUserInfo.SetInfoSale(harvestableZoneToUpdate.harvestableZone, harvestableZoneToUpdate.infoSale);

            updatedUserInfosToUpdate =  updatedUserInfosToUpdate.stream().map(userInfo -> {
                if (userInfo.id.equals(userInfoToUpdate.id)) {
                    return userInfoToUpdate;
                }
                return userInfo;
            }).collect(Collectors.toList());
        }
        return updatedUserInfosToUpdate;
    }
}
