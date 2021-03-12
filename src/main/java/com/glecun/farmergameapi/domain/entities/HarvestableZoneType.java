package com.glecun.farmergameapi.domain.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum HarvestableZoneType {
    ZONE_1(false, 12, 0),
    ZONE_2(false, 12, 0),
    ZONE_3(true, 12, 2000),
    ZONE_4(true, 12, 2000),
    ZONE_5(true, 40, 5000);

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
