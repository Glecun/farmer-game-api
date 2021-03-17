package com.glecun.farmergameapi.domain.entities;

public enum SeedEnum {

    GREEN_BEAN(new Seed(GrowthTime.GROWTH_TIME_1, new PriceRange(1,1), new PriceRange(2,5))),
    BEETS(new Seed(GrowthTime.GROWTH_TIME_1, new PriceRange(1,1), new PriceRange(2,5))),
    CARROT(new Seed(GrowthTime.GROWTH_TIME_1, new PriceRange(1,1), new PriceRange(2,5))),

    PEA(new Seed(GrowthTime.GROWTH_TIME_2, new PriceRange(10,10), new PriceRange(20,50))),
    POTATO(new Seed(GrowthTime.GROWTH_TIME_2, new PriceRange(10,10), new PriceRange(20,50))),
    GRAPE(new Seed(GrowthTime.GROWTH_TIME_2, new PriceRange(10,10), new PriceRange(20,50))),
    BLUEBERRY(new Seed(GrowthTime.GROWTH_TIME_2, new PriceRange(10,10), new PriceRange(20,50))),

    PEPPER_GREEN(new Seed(GrowthTime.GROWTH_TIME_3, new PriceRange(60,60), new PriceRange(120,300))),
    PEPPER_YELLOW(new Seed(GrowthTime.GROWTH_TIME_3, new PriceRange(60,60), new PriceRange(120,300))),
    PEPPER_RED(new Seed(GrowthTime.GROWTH_TIME_3, new PriceRange(60,60), new PriceRange(120,300))),

    STRAWBERRY(new Seed(GrowthTime.GROWTH_TIME_4, new PriceRange(120,120), new PriceRange(240,600))),
    ONION(new Seed(GrowthTime.GROWTH_TIME_4, new PriceRange(120,120), new PriceRange(240,600))),
    CUCUMBER(new Seed(GrowthTime.GROWTH_TIME_4, new PriceRange(120,120), new PriceRange(240,600))),

    TOMATO(new Seed(GrowthTime.GROWTH_TIME_5, new PriceRange(360,360), new PriceRange(720,1800))),
    MELON(new Seed(GrowthTime.GROWTH_TIME_5, new PriceRange(360,360), new PriceRange(720,1800))),
    CORN(new Seed(GrowthTime.GROWTH_TIME_5, new PriceRange(360,360), new PriceRange(720,1800))),

    CABBAGE(new Seed(GrowthTime.GROWTH_TIME_6, new PriceRange(1440,1440), new PriceRange(2880,7200))),
    GARLIC(new Seed(GrowthTime.GROWTH_TIME_6, new PriceRange(1440,1440), new PriceRange(2880,7200))),
    CAULIFLOWER(new Seed(GrowthTime.GROWTH_TIME_6, new PriceRange(1440,1440), new PriceRange(2880,7200))),

    WHEAT(new Seed(GrowthTime.GROWTH_TIME_7, new PriceRange(2880,2880), new PriceRange(5760,14400))),
    SUGAR_CANE(new Seed(GrowthTime.GROWTH_TIME_7, new PriceRange(2880,2880), new PriceRange(5760,14400))),
    ASPARAGUS(new Seed(GrowthTime.GROWTH_TIME_7, new PriceRange(2880,2880), new PriceRange(5760,14400)));

    public final Seed seed;

    SeedEnum(Seed seed) {
        this.seed = seed;
    }
}
