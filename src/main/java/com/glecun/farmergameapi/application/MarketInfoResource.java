package com.glecun.farmergameapi.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.glecun.farmergameapi.application.dto.MarketInfoJson;
import com.glecun.farmergameapi.domain.ApplicationDomain;

@RestController
@RequestMapping("market-info")
public class MarketInfoResource {

   private final ApplicationDomain applicationDomain;

   @Autowired
   public MarketInfoResource(ApplicationDomain applicationDomain) {
      this.applicationDomain = applicationDomain;
   }


   @GetMapping("/")
   public MarketInfoJson getMarketInfo() {
      return applicationDomain.getCurrentMarketInfo()
            .map(MarketInfoJson::from)
            .orElseThrow(() -> new RuntimeException("No Market info available"));
   }
}
