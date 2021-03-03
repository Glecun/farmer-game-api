package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.Demand;
import com.glecun.farmergameapi.domain.entities.DemandType;

public class DemandMongo {
    public final DemandType demandType;
    public final int nbDemand;

    public DemandMongo(DemandType demandType, int nbDemand) {
        this.demandType = demandType;
        this.nbDemand = nbDemand;
    }

    public static DemandMongo from(Demand demand) {
        return new DemandMongo(demand.demandType, demand.nbDemand);
    }

    public Demand toDemand() {
        return new Demand(demandType, nbDemand);
    }
}
