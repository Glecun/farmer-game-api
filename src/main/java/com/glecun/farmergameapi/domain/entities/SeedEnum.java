package com.glecun.farmergameapi.domain.entities;

public enum SeedEnum {

    GREEN_BEAN(new Seed(GrowthTime.GROWTH_TIME_1, new PriceRange(1,1), new PriceRange(2,3))),
    BEETS(new Seed(GrowthTime.GROWTH_TIME_1, new PriceRange(1,1), new PriceRange(2,3))),
    CARROT(new Seed(GrowthTime.GROWTH_TIME_1, new PriceRange(1,1), new PriceRange(2,3))),
    PEA(new Seed(GrowthTime.GROWTH_TIME_2, new PriceRange(2,2), new PriceRange(6,7))),
    POTATO(new Seed(GrowthTime.GROWTH_TIME_2, new PriceRange(2,2), new PriceRange(6,7))),
    GRAPE(new Seed(GrowthTime.GROWTH_TIME_3, new PriceRange(5,5), new PriceRange(32,34))),
    BLUEBERRY(new Seed(GrowthTime.GROWTH_TIME_4, new PriceRange(29,29), new PriceRange(92,105))),
    PEPPER_GREEN(new Seed(GrowthTime.GROWTH_TIME_5, new PriceRange(76,76), new PriceRange(240,260))),
    PEPPER_YELLOW(new Seed(GrowthTime.GROWTH_TIME_5, new PriceRange(76,76), new PriceRange(240,260))),
    PEPPER_RED(new Seed(GrowthTime.GROWTH_TIME_5, new PriceRange(76,76), new PriceRange(240,260))),
    STRAWBERRY(new Seed(GrowthTime.GROWTH_TIME_6, new PriceRange(184,184), new PriceRange(589,648))),
    ONION(new Seed(GrowthTime.GROWTH_TIME_7, new PriceRange(464,464), new PriceRange(1230,1353))),
    CUCUMBER(new Seed(GrowthTime.GROWTH_TIME_8, new PriceRange(889,889), new PriceRange(2845,3130))),
    TOMATO(new Seed(GrowthTime.GROWTH_TIME_9, new PriceRange(2241,2241), new PriceRange(5939,6533))),
    MELON(new Seed(GrowthTime.GROWTH_TIME_10, new PriceRange(4292,4292), new PriceRange(12161,13377))),
    CORN(new Seed(GrowthTime.GROWTH_TIME_11, new PriceRange(9085,9085), new PriceRange(29072,31979))),
    CABBAGE(new Seed(GrowthTime.GROWTH_TIME_12, new PriceRange(22894,22894), new PriceRange(53114,58425))),
    GARLIC(new Seed(GrowthTime.GROWTH_TIME_13, new PriceRange(35531,35531), new PriceRange(113699,125069))),
    CAULIFLOWER(new Seed(GrowthTime.GROWTH_TIME_14, new PriceRange(89538,89538), new PriceRange(237276,261004))),
    WHEAT(new Seed(GrowthTime.GROWTH_TIME_15, new PriceRange(171466,171466), new PriceRange(422949,465244))),
    SUGAR_CANE(new Seed(GrowthTime.GROWTH_TIME_16, new PriceRange(293778,293778), new PriceRange(940090,1034099))),
    ASPARAGUS(new Seed(GrowthTime.GROWTH_TIME_17, new PriceRange(740321,740321), new PriceRange(2165439,2381983)));

    public final Seed seed;

    SeedEnum(Seed seed) {
        this.seed = seed;
    }
}
