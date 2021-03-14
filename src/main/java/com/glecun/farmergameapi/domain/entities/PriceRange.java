package com.glecun.farmergameapi.domain.entities;

import java.util.Random;

public class PriceRange {
    public final int min;
    public final int max;

    public PriceRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int randomizeInRange() {
        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().orElse(min);
    }

    public int randomizeInRangeAccordingTo(Demand demand) {
        var modifiedMin = min;
        var modifiedMax = max;
        float twentyPercent = (max - min) * 0.2f;
        if(demand.demandType.equals(DemandType.VerySmallDemand)) {
            modifiedMin = Math.round(min +4*twentyPercent);
        }
        if(demand.demandType.equals(DemandType.SmallDemand)) {
            modifiedMin = Math.round(min +3*twentyPercent);
            modifiedMax = Math.round(min +4*twentyPercent);
        }
        if(demand.demandType.equals(DemandType.MediumDemand)) {
            modifiedMin = Math.round(min +2*twentyPercent);
            modifiedMax = Math.round(min +3*twentyPercent);
        }
        if(demand.demandType.equals(DemandType.HighDemand)) {
            modifiedMin = Math.round(min +twentyPercent);
            modifiedMax = Math.round(min +2*twentyPercent);
        }
        if(demand.demandType.equals(DemandType.VeryHighDemand)) {
            modifiedMax = Math.round(min + twentyPercent);
        }
        Random r = new Random();
        return r.ints(modifiedMin, (modifiedMax + 1)).findFirst().orElse(min);
    }
}
