package com.glecun.farmergameapi.domain.entities;

public enum SeedEnum {

    GREEN_BEAN(new Seed(GrowthTime.FIRST_GROWTH_TIME, new PriceRange(5,10), new PriceRange(11,15))),
    BEETS(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)));

    public final Seed seed;

    SeedEnum(Seed seed) {
        this.seed = seed;
    }
}
