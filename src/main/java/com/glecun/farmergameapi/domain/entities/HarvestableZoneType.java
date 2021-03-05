package com.glecun.farmergameapi.domain.entities;

public enum HarvestableZoneType {
    ZONE_1(false, 12),
    ZONE_2(false, 12),
    ZONE_3(true, 40);

    public final boolean lockedByDefaut;
    public final int nbOfZone;


    HarvestableZoneType(boolean lockedByDefaut, int nbOfZone) {
        this.lockedByDefaut = lockedByDefaut;
        this.nbOfZone = nbOfZone;
    }
}
