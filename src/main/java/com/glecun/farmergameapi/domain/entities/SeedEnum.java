package com.glecun.farmergameapi.domain.entities;

public enum SeedEnum {

    GREEN_BEAN(new Seed(GrowthTime.FIRST_GROWTH_TIME, new PriceRange(5,10), new PriceRange(11,15)), 10),
    BEETS(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    CARROT(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    PEA(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    POTATO(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    GRAPE(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    BLUEBERRY(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    PEPPER_GREEN(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    PEPPER_YELLOW(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    PEPPER_RED(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    STRAWBERRY(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    ONION(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    CUCUMBER(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    TOMATO(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    MELON(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    CORN(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    CABBAGE(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    GARLIC(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    CAULIFLOWER(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    WHEAT(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    SUGAR_CANE(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10),
    ASPARAGUS(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)), 10);

    public final Seed seed;
    public final int priceToUnlock;

    SeedEnum(Seed seed, int priceToUnlock) {
        this.seed = seed;
        this.priceToUnlock = priceToUnlock;
    }
}
