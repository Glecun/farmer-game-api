package com.glecun.farmergameapi.application.dto;

import com.glecun.farmergameapi.domain.entities.GrowthTime;
import com.glecun.farmergameapi.domain.entities.PriceRange;

public class SeedJson {
   public final GrowthTimeJson growthTime;
   public final PriceRangeJson buyPrice;
   public final PriceRangeJson sellPrice;

   public SeedJson(GrowthTimeJson growthTime, PriceRangeJson buyPrice, PriceRangeJson sellPrice) {
      this.growthTime = growthTime;
      this.buyPrice = buyPrice;
      this.sellPrice = sellPrice;
   }

}
