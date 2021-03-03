package com.glecun.farmergameapi.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.glecun.farmergameapi.domain.ApplicationDomain;
import com.glecun.farmergameapi.domain.entities.GrowthTime;

@Component
public class CronByMinGrowthTime {


   private final ApplicationDomain applicationDomain;

   @Autowired
   public CronByMinGrowthTime(ApplicationDomain applicationDomain) {
      this.applicationDomain = applicationDomain;
   }

   @Scheduled(fixedRate = 20000)
   public void firstMinGrowthTime() {
      applicationDomain.generateMarketInfos(GrowthTime.FIRST_GROWTH_TIME);
   }

   @Scheduled(fixedRate = 120000)
   public void secondMinGrowthTime() {
      applicationDomain.generateMarketInfos(GrowthTime.SECOND_GROWTH_TIME);
   }
}
