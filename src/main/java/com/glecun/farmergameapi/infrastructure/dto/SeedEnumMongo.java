package com.glecun.farmergameapi.infrastructure.dto;


public enum SeedEnumMongo {
    GREEN_BEAN(new SeedMongo(GrowthTimeMongo.FIRST_GROWTH_TIME, new PriceRangeMongo(5,10), new PriceRangeMongo(11,15))),
    BEETS(new SeedMongo(GrowthTimeMongo.SECOND_GROWTH_TIME, new PriceRangeMongo(8,13), new PriceRangeMongo(11,15)));

    public final SeedMongo seedMongo;

    SeedEnumMongo(SeedMongo seedMongo) {
        this.seedMongo = seedMongo;
    }
}
