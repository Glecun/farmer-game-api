package com.glecun.farmergameapi.domain;

import com.glecun.farmergameapi.domain.entities.*;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.glecun.farmergameapi.domain.ApplicationDomain.NB_OF_FAKE_USERS;
import static com.glecun.farmergameapi.domain.entities.HarvestableZoneType.getNbZonesSumList;

@Service
public class ResolveSales {

    private final UserInfoPort userInfoPort;
    private boolean fakeUserUsed = true;
    private Function<Demand, Integer> randomizeNbFakePlayers = this::randomizeNbFakePlayersAccordingToDemand;

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
            long nbOfFakeUserInvolved = randomizeNbFakePlayers.apply(onSaleSeedConcerned.demand);
            int nbOfZoneFakeUserTakes = fakeUserUsed ? nbOfZoneFakeUsersTake(nbOfFakeUserInvolved) : 0;
            int nbTotalHarvestable = getNbTotalHarvestable(seedEnum, userConcerned) + nbOfZoneFakeUserTakes;
            int nbFarmer = userConcerned.size() + (fakeUserUsed ? (int)nbOfFakeUserInvolved : 0);
            long nbTotalFarmer = userInfoPort.countAll() + NB_OF_FAKE_USERS;

            List<UserInfo> userInfosToUpdate = userConcerned.stream()
                    .map(userInfo -> getUserInfoToSave(seedEnum, onSaleSeedConcerned, nbTotalHarvestable, nbFarmer, userInfo, nbTotalFarmer))
                    .collect(Collectors.toList());

            int nbHarvestableNotSold = nbTotalHarvestable - onSaleSeedConcerned.demand.nbDemand;
            userInfosToUpdate = maybeReduceSales(userInfosToUpdate, nbHarvestableNotSold, seedEnum, onSaleSeedConcerned,nbOfZoneFakeUserTakes);

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

    private List<UserInfo> maybeReduceSales(List<UserInfo> userInfosToUpdate, int nbHarvestableNotSold, SeedEnum seedEnum, OnSaleSeed onSaleSeedConcerned, int nbOfZoneFakeUserTakes) {

        List<UserInfo> updatedUserInfosToUpdate = new ArrayList<>(userInfosToUpdate);

        List<String> userByNbHarvestable = userInfosToUpdate.stream()
                .collect(Collectors.toMap(userInfo -> userInfo.email, userInfo -> userInfo.getNbHarvestable(seedEnum)))
                .entrySet().stream()
                .map(harvestable -> IntStream.range(0, harvestable.getValue()).boxed().map(integer -> harvestable.getKey()).collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        if (fakeUserUsed) {
            List<String> fakeUsers = IntStream.range(0, nbOfZoneFakeUserTakes).boxed().map(integer -> "FAKEUSER").collect(Collectors.toList());
            userByNbHarvestable.addAll(fakeUsers);
        }

        for (int i = 0 ; i < nbHarvestableNotSold ; i++) {
            if(userByNbHarvestable.size() == 0){ break; }
            int randomIndex = new Random().nextInt(userByNbHarvestable.size());
            String userSelected = userByNbHarvestable.remove(randomIndex);

            if (userSelected.equals("FAKEUSER")){
                continue;
            }

            var randomUserSelected = updatedUserInfosToUpdate.stream()
                    .filter(userInfo -> userInfo.email.equals(userSelected))
                    .findFirst().orElseThrow();

            var harvestableZoneToUpdate =  randomUserSelected.harvestableZones.stream()
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
            var userInfoToUpdate = randomUserSelected.SetInfoSale(harvestableZoneToUpdate.harvestableZone, harvestableZoneToUpdate.infoSale);

            updatedUserInfosToUpdate =  updatedUserInfosToUpdate.stream().map(userInfo -> {
                if (userInfo.id.equals(userInfoToUpdate.id)) {
                    return userInfoToUpdate;
                }
                return userInfo;
            }).collect(Collectors.toList());
        }
        return updatedUserInfosToUpdate;
    }

    private int nbOfZoneFakeUsersTake(long nbOfFakeUserInvolved) {
        List<Integer> nbOfZoneList = getNbZonesSumList();
        return IntStream.range(0, (int)nbOfFakeUserInvolved)
                .map(operand -> nbOfZoneList.get(new Random().nextInt(nbOfZoneList.size())))
                .reduce(0, Integer::sum);

    }

    private int randomizeNbFakePlayersAccordingToDemand(Demand demand) {
        var minNbFakeUser = 0;
        var maxNbFakeUser = NB_OF_FAKE_USERS;

        int twentyPercent = 2;

        if(demand.demandType.equals(DemandType.VerySmallDemand)) {
            maxNbFakeUser = twentyPercent;
        }
        if(demand.demandType.equals(DemandType.SmallDemand)) {
            minNbFakeUser = twentyPercent;
            maxNbFakeUser = 2 * twentyPercent;
        }
        if(demand.demandType.equals(DemandType.MediumDemand)) {
            minNbFakeUser = 2 * twentyPercent;
            maxNbFakeUser = 3 * twentyPercent;
        }
        if(demand.demandType.equals(DemandType.HighDemand)) {
            minNbFakeUser = 3 * twentyPercent;
            maxNbFakeUser = 4 * twentyPercent;
        }
        if(demand.demandType.equals(DemandType.VeryHighDemand)) {
            minNbFakeUser = 4 * twentyPercent;
        }

        Random r = new Random();
        return r.ints(minNbFakeUser, (maxNbFakeUser + 1)).findFirst().orElse(minNbFakeUser);
    }
}
