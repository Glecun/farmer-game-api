package com.glecun.farmergameapi.domain.entities;

public enum DemandType {
    VeryHighDemand(70),
    HighDemand(55),
    MediumDemand(40),
    SmallDemand(25),
    VerySmallDemand(10);

    public final int percentOfNbZones;

    DemandType(int percentOfNbZones) {
        this.percentOfNbZones = percentOfNbZones;
    }
}
