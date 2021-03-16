package com.glecun.farmergameapi.domain.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum HarvestableZoneType {
    ZONE_1_TIER_1(false, 12, 0),
    ZONE_2_TIER_1(true, 12, 10),
    ZONE_3_TIER_1(true, 12, 10),
    ZONE_4_TIER_1(true, 12, 10),
    ZONE_5_TIER_1(true, 40, 10),

    ZONE_1_TIER_2(false, 12, 0),
    ZONE_2_TIER_2(true, 12, 10),
    ZONE_3_TIER_2(true, 12, 10),
    ZONE_4_TIER_2(true, 12, 10),
    ZONE_5_TIER_2(true, 40, 10),

    ZONE_1_TIER_3(false, 12, 0),
    ZONE_2_TIER_3(true, 12, 10),
    ZONE_3_TIER_3(true, 12, 10),
    ZONE_4_TIER_3(true, 12, 10),
    ZONE_5_TIER_3(true, 40, 10),

    ZONE_1_TIER_4(false, 12, 0),
    ZONE_2_TIER_4(true, 12, 10),
    ZONE_3_TIER_4(true, 12, 10),
    ZONE_4_TIER_4(true, 12, 10),
    ZONE_5_TIER_4(true, 40, 10),

    ZONE_1_TIER_5(false, 12, 0),
    ZONE_2_TIER_5(true, 12, 10),
    ZONE_3_TIER_5(true, 12, 10),
    ZONE_4_TIER_5(true, 12, 10),
    ZONE_5_TIER_5(true, 40, 10),

    ZONE_1_TIER_6(false, 12, 0),
    ZONE_2_TIER_6(true, 12, 10),
    ZONE_3_TIER_6(true, 12, 10),
    ZONE_4_TIER_6(true, 12, 10),
    ZONE_5_TIER_6(true, 40, 10),


    ZONE_1_TIER_7(false, 12, 0),
    ZONE_2_TIER_7(true, 12, 10),
    ZONE_3_TIER_7(true, 12, 10),
    ZONE_4_TIER_7(true, 12, 10),
    ZONE_5_TIER_7(true, 40, 10);

    public final boolean lockedByDefault;
    public final int nbOfZone;
    public final int price;


    HarvestableZoneType(boolean lockedByDefault, int nbOfZone, int price) {
        this.lockedByDefault = lockedByDefault;
        this.nbOfZone = nbOfZone;
        this.price = price;
    }

    public static Integer getMaxCapacity() {
        return Arrays.stream(HarvestableZoneType.values())
                .map(harvestableZoneType -> harvestableZoneType.nbOfZone)
                .collect(Collectors.toList()).stream().reduce(0, Integer::sum);
    }

    public static List<Integer> getNbZonesSumList() {
        return Arrays.stream(HarvestableZoneType.values())
                .map(harvestableZoneType -> harvestableZoneType.nbOfZone)
                .reduce(new ArrayList<Integer>(), (nbOfZonesSumList, nbOfZone) -> {
                    var newNbOfZonesSumList = new ArrayList<Integer>(nbOfZonesSumList);
                    newNbOfZonesSumList.add(newNbOfZonesSumList.stream().reduce(0, Integer::sum) + nbOfZone);
                    return newNbOfZonesSumList;
                }, (a, b) -> b);
    }
}
