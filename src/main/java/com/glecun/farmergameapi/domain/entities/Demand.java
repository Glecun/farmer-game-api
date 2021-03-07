package com.glecun.farmergameapi.domain.entities;

import java.util.Objects;

public class Demand {
    public final DemandType demandType;
    public final int nbDemand;

    public Demand(DemandType demandType, int nbDemand) {
        this.demandType = demandType;
        this.nbDemand = nbDemand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Demand demand = (Demand) o;
        return nbDemand == demand.nbDemand &&
                demandType == demand.demandType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(demandType, nbDemand);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Demand{");
        sb.append("demandType=").append(demandType);
        sb.append(", nbDemand=").append(nbDemand);
        sb.append('}');
        return sb.toString();
    }
}
