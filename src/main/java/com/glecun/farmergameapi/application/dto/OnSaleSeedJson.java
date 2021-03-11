package com.glecun.farmergameapi.application.dto;

import java.time.LocalDateTime;
import com.glecun.farmergameapi.domain.entities.OnSaleSeed;
import com.glecun.farmergameapi.domain.entities.SeedEnum;

public class OnSaleSeedJson {
   public final SeedEnum seedEnum;
   public final int buyPrice;
   public final int sellPrice;
   public final DemandJson demand;
   public final long timeLeft;
   public final LocalDateTime willBeSoldDate;

   private OnSaleSeedJson(SeedEnum seedEnum, int buyPrice, int sellPrice, DemandJson demand, long timeLeft, LocalDateTime willBeSoldDate) {
      this.seedEnum = seedEnum;
      this.buyPrice = buyPrice;
      this.sellPrice = sellPrice;
      this.demand = demand;
      this.timeLeft = timeLeft;
      this.willBeSoldDate = willBeSoldDate;
   }

   public static OnSaleSeedJson from(OnSaleSeed onSaleSeed) {
      return new OnSaleSeedJson(
            onSaleSeed.seedEnum,
            onSaleSeed.buyPrice,
            onSaleSeed.sellPrice,
            DemandJson.from(onSaleSeed.demand),
            onSaleSeed.timeLeft(),
            onSaleSeed.willBeSoldDate
      );
   }

   public OnSaleSeed toOnSaleSeed() {
      return OnSaleSeed.builder()
              .seedEnum(seedEnum)
              .buyPrice(buyPrice)
              .sellPrice(sellPrice)
              .demand(demand.toDemand())
              .willBeSoldDate(willBeSoldDate)
              .onSaleDate(null)
              .build();
   }
}
