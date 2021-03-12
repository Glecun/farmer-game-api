package com.glecun.farmergameapi.domain;

import com.glecun.farmergameapi.domain.entities.*;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.glecun.farmergameapi.domain.ApplicationDomain.NB_OF_FAKE_USERS;
import static com.glecun.farmergameapi.domain.entities.HarvestableZoneType.getNbZonesSumList;

@Service
public class ResolveSales {

    private final UserInfoPort userInfoPort;
    private boolean fakeUserUsed = true;

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
            long nbOfFakeUserInvolved = RandomizeNbOfFakeUserParticipating();
            int nbTotalHarvestable = getNbTotalHarvestable(seedEnum, userConcerned) + (fakeUserUsed ? nbOfZoneFakeUsersTake(nbOfFakeUserInvolved) : 0);
            int nbFarmer = userConcerned.size() + (fakeUserUsed ? (int)nbOfFakeUserInvolved : 0);
            long nbTotalFarmer = userInfoPort.countAll() + NB_OF_FAKE_USERS;

            List<UserInfo> userInfosToUpdate = userConcerned.stream()
                    .map(userInfo -> getUserInfoToSave(seedEnum, onSaleSeedConcerned, nbTotalHarvestable, nbFarmer, userInfo, nbTotalFarmer))
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
    private UserInfo getUserInfoToSave(SeedEnum seedEnum, OnSaleSeed onSaleSeedConcerned, int nbTotalHarvestable, int nbFarmer, UserInfo userInfo, long nbTotalFarmer) {
        return userInfo.harvestableZones.stream()
                .map(harvestableZone -> harvestableZone.getHarvestablePlanted()
                        .filter(harvestablePlanted -> harvestablePlanted.seedsPlanted.seedEnum == seedEnum)
                        .filter(harvestablePlanted -> harvestablePlanted.getInfoSale().isEmpty())
                        .filter(harvestablePlanted -> harvestablePlanted.seedsPlanted.canBeSell())
                        .map(harvestablePlanted -> new HarvestableZoneToUpdate(
                                harvestableZone,
                                new InfoSale(
                                        nbFarmer,
                                        nbTotalFarmer,
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
                .filter(userInfo -> userInfo.hasHarvestablePlantedWithSeedEnumAndInfoSaleEmptyAndOldOnSaleDate(seedEnum))
                .collect(Collectors.toList());
    }

    private int getNbTotalHarvestable(SeedEnum seedEnum, List<UserInfo> userConcerned) {
        return userConcerned.stream()
                .map(userInfo -> userInfo.harvestableZones).flatMap(List::stream)
                .map(harvestableZone -> harvestableZone.getHarvestablePlanted()
                        .filter(harvestablePlanted -> harvestablePlanted.seedsPlanted.seedEnum == seedEnum && harvestablePlanted.getInfoSale().isEmpty() && harvestablePlanted.seedsPlanted.canBeSell())
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
            if (isFakeUserNeedToReduce(updatedUserInfosToUpdate) && fakeUserUsed){
                continue;
            }
            if(updatedUserInfosToUpdate.stream().noneMatch(userInfo -> userInfo.hasStillHarvestableSold(seedEnum))){
                break;
            }
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
                                                        infoSale.nbTotalFarmer,
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

    private boolean isFakeUserNeedToReduce(List<UserInfo> updatedUserInfosToUpdate) {
        int nbUsers = updatedUserInfosToUpdate.size();
        return new Random().nextInt(nbUsers + NB_OF_FAKE_USERS) >= nbUsers;
    }

    private int nbOfZoneFakeUsersTake(long nbOfFakeUserInvolved) {
        List<Integer> nbOfZoneList = getNbZonesSumList();
        return IntStream.range(0, (int)nbOfFakeUserInvolved + 1)
                .map(operand -> nbOfZoneList.get(new Random().nextInt(nbOfZoneList.size())))
                .reduce(0, Integer::sum);

    }

    private long RandomizeNbOfFakeUserParticipating() {
        return IntStream.range(0, new Random().nextInt(NB_OF_FAKE_USERS + 1))
                .filter(value -> new Random().nextInt(2) == 0)
                .count();
    }
}
