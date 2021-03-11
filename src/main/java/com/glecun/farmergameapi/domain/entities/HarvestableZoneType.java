package com.glecun.farmergameapi.domain.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum HarvestableZoneType {
    ZONE_1(false, 12),
    ZONE_2(false, 12),
    ZONE_3(true, 12),
    ZONE_4(true, 12),
    ZONE_5(true, 40);

    public final boolean lockedByDefaut;
    public final int nbOfZone;


    HarvestableZoneType(boolean lockedByDefaut, int nbOfZone) {
        this.lockedByDefaut = lockedByDefaut;
        this.nbOfZone = nbOfZone;
    }

    public static Integer getMaxCapacity() {
        return Arrays.stream(HarvestableZoneType.values())
                .filter(harvestableZoneType -> !harvestableZoneType.lockedByDefaut)
                .map(harvestableZoneType -> harvestableZoneType.nbOfZone)
                .collect(Collectors.toList()).stream().reduce(0, Integer::sum);
    }

    public static List<Integer> getNbZonesAdditionnedList() {
        return Arrays.stream(HarvestableZoneType.values())
                .filter(harvestableZoneType -> !harvestableZoneType.lockedByDefaut)
                .map(harvestableZoneType -> harvestableZoneType.nbOfZone)
                .reduce(new ArrayList<Integer>(), (list, nbZonesAdditionned) -> {
                    var newList = new ArrayList<Integer>(list);
                    newList.add(newList.stream().reduce(0, Integer::sum) + nbZonesAdditionned);
                    return newList;
                }, (a, b) -> b);
    }
}
