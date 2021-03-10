package com.glecun.farmergameapi.domain.entities;

public enum SeedEnum {

    GREEN_BEAN(new Seed(GrowthTime.FIRST_GROWTH_TIME, new PriceRange(5,10), new PriceRange(11,15))),
    BEETS(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    CARROT(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    PEA(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    POTATO(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    GRAPE(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    BLUEBERRY(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    PEPPER_GREEN(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    PEPPER_YELLOW(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    PEPPER_RED(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    STRAWBERRY(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    ONION(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    CUCUMBER(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    TOMATO(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    MELON(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    CORN(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    CABBAGE(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    GARLIC(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    CAULIFLOWER(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    WHEAT(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    SUGAR_CANE(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15))),
    ASPARAGUS(new Seed(GrowthTime.SECOND_GROWTH_TIME, new PriceRange(8,13), new PriceRange(11,15)));

    public final Seed seed;

    SeedEnum(Seed seed) {
        this.seed = seed;
    }
}
