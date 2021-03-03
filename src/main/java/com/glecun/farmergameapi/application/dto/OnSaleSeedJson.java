package com.glecun.farmergameapi.application.dto;

import java.time.LocalDateTime;
import com.glecun.farmergameapi.domain.entities.OnSaleSeed;

public class OnSaleSeedJson {
   public final SeedEnumJson seedEnum;
   public final int buyPrice;
   public final int sellPrice;
   public final DemandJson demand;
   public final long timeLeft;
   public final LocalDateTime willBeSoldDate;

   private OnSaleSeedJson(SeedEnumJson seedEnum, int buyPrice, int sellPrice, DemandJson demand, long timeLeft, LocalDateTime willBeSoldDate) {
      this.seedEnum = seedEnum;
      this.buyPrice = buyPrice;
      this.sellPrice = sellPrice;
      this.demand = demand;
      this.timeLeft = timeLeft;
      this.willBeSoldDate = willBeSoldDate;
   }

   public static OnSaleSeedJson from(OnSaleSeed onSaleSeed) {
      return new OnSaleSeedJson(
            SeedEnumJson.values()[onSaleSeed.seedEnum.ordinal()],
            onSaleSeed.buyPrice,
            onSaleSeed.sellPrice,
            DemandJson.from(onSaleSeed.demand),
            onSaleSeed.timeLeft(),
            onSaleSeed.willBeSoldDate
      );
   }
}
