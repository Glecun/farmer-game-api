package com.glecun.farmergameapi.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.glecun.farmergameapi.domain.entities.MarketInfo;
import com.glecun.farmergameapi.domain.entities.OnSaleSeed;

public class MarketInfoJson {

   public final List<OnSaleSeedJson> onSaleSeeds;
   public final LocalDateTime marketTime;

   public MarketInfoJson(List<OnSaleSeedJson> onSaleSeeds, LocalDateTime marketTime) {
      this.onSaleSeeds = onSaleSeeds;
      this.marketTime = marketTime;
   }

   public static MarketInfoJson from(MarketInfo marketInfo) {
      return new MarketInfoJson(
            marketInfo.onSaleSeeds.stream().map(OnSaleSeedJson::from).collect(Collectors.toList()),
            marketInfo.marketTime
      );
   }
}
