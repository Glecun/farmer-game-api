package com.glecun.farmergameapi.domain.entities;

public enum GrowthTime {
    GROWTH_TIME_1(0.5, 0.33333333),
    GROWTH_TIME_2(1, 0.66666666),
    GROWTH_TIME_3(5, 3.33333333),
    GROWTH_TIME_4(10, 6.66666666),
    GROWTH_TIME_5(20, 13.33333333),
    GROWTH_TIME_6(40, 26.66666666),
    GROWTH_TIME_7(60, 40),
    GROWTH_TIME_8(120, 80),
    GROWTH_TIME_9(180, 120),
    GROWTH_TIME_10(300, 200),
    GROWTH_TIME_11(600, 400),
    GROWTH_TIME_12(720, 480),
    GROWTH_TIME_13(1440, 960),
    GROWTH_TIME_14(2160, 1440),
    GROWTH_TIME_15(2880, 1920),
    GROWTH_TIME_16(5760, 3840),
    GROWTH_TIME_17(10080, 6720);

    public final double growthTime;
    public final double minGrowthTime;

    GrowthTime(double growthTime, double minGrowthTime) {
        this.growthTime = growthTime;
        this.minGrowthTime = minGrowthTime;
    }
}
