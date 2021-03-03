package com.glecun.farmergameapi.infrastructure.dto;

public enum GrowthTimeMongo {
    FIRST_GROWTH_TIME(0.5, 0.33333333),
    SECOND_GROWTH_TIME(3, 2);

    public final double growthTime;
    public final double minGrowthTime;

    GrowthTimeMongo(double growthTime, double minGrowthTime) {
        this.growthTime = growthTime;
        this.minGrowthTime = minGrowthTime;
    }
}
