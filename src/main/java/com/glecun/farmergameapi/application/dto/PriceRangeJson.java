package com.glecun.farmergameapi.application.dto;

public class PriceRangeJson {
   public final int min;
   public final int max;

   public PriceRangeJson(int min, int max) {
      this.min = min;
      this.max = max;
   }
}
