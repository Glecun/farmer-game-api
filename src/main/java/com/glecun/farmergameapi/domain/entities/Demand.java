package com.glecun.farmergameapi.domain.entities;

public class Demand {
    public final DemandType demandType;
    public final int nbDemand;

    public Demand(DemandType demandType, int nbDemand) {
        this.demandType = demandType;
        this.nbDemand = nbDemand;
    }
}
