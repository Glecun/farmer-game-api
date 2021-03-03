package com.glecun.farmergameapi.application.dto;

public enum SeedEnumJson {

   GREEN_BEAN(new SeedJson(GrowthTimeJson.FIRST_GROWTH_TIME, new PriceRangeJson(5,10), new PriceRangeJson(11,15))),
   BEETS(new SeedJson(GrowthTimeJson.SECOND_GROWTH_TIME, new PriceRangeJson(8,13), new PriceRangeJson(11,15)));

   public final SeedJson seed;

   SeedEnumJson(SeedJson seed) {
      this.seed = seed;
   }
}
