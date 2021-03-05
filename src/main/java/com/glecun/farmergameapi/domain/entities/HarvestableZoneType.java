package com.glecun.farmergameapi.domain.entities;

public enum HarvestableZoneType {
    ZONE_1(false, 9),
    ZONE_2(false, 9),
    ZONE_3(true, 27);

    public final boolean lockedByDefaut;
    public final int nbOfZone;


    HarvestableZoneType(boolean lockedByDefaut, int nbOfZone) {
        this.lockedByDefaut = lockedByDefaut;
        this.nbOfZone = nbOfZone;
    }
}
