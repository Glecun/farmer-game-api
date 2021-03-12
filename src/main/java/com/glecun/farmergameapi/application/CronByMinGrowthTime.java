package com.glecun.farmergameapi.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.glecun.farmergameapi.domain.ApplicationDomain;
import com.glecun.farmergameapi.domain.entities.GrowthTime;

@Component
public class CronByMinGrowthTime {

   Logger logger = LoggerFactory.getLogger(CronByMinGrowthTime.class);

   private final ApplicationDomain applicationDomain;

   @Autowired
   public CronByMinGrowthTime(ApplicationDomain applicationDomain) {
      this.applicationDomain = applicationDomain;
   }

   @Scheduled(fixedRate = 20000)
   public void firstMinGrowthTime() {
      logger.info("Start firstMinGrowthTime");
      applicationDomain.generateMarketInfos(GrowthTime.FIRST_GROWTH_TIME);
      logger.info("Finish firstMinGrowthTime");
   }

   @Scheduled(fixedRate = 120000)
   public void secondMinGrowthTime() {
      logger.info("Start secondMinGrowthTime");
      applicationDomain.generateMarketInfos(GrowthTime.SECOND_GROWTH_TIME);
      logger.info("Finish secondMinGrowthTime");
   }
}
