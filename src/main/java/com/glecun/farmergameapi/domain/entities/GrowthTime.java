package com.glecun.farmergameapi.domain.entities;

public enum GrowthTime {
    GROWTH_TIME_1(0.5, 0.33333333),
    GROWTH_TIME_2(5, 3.33333333),
    GROWTH_TIME_3(30, 20),
    GROWTH_TIME_4(60, 40),
    GROWTH_TIME_5(180, 120),
    GROWTH_TIME_6(720, 480),
    GROWTH_TIME_7(1440, 960);

    public final double growthTime;
    public final double minGrowthTime;

    GrowthTime(double growthTime, double minGrowthTime) {
        this.growthTime = growthTime;
        this.minGrowthTime = minGrowthTime;
    }
}
