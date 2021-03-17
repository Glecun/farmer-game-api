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
   public void MinGrowthTime_1() {
      logger.info("Start MinGrowthTime_1");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_1);
      logger.info("Finish MinGrowthTime_1");
   }

   @Scheduled(fixedRate = 200000)
   public void MinGrowthTime_2() {
      logger.info("Start MinGrowthTime_2");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_2);
      logger.info("Finish MinGrowthTime_2");
   }

   @Scheduled(fixedRate = 1200000)
   public void MinGrowthTime_3() {
      logger.info("Start MinGrowthTime_3");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_3);
      logger.info("Finish MinGrowthTime_3");
   }

   @Scheduled(fixedRate = 2400000)
   public void MinGrowthTime_4() {
      logger.info("Start MinGrowthTime_4");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_4);
      logger.info("Finish MinGrowthTime_4");
   }

   @Scheduled(fixedRate = 7200000)
   public void MinGrowthTime_5() {
      logger.info("Start MinGrowthTime_5");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_5);
      logger.info("Finish MinGrowthTime_5");
   }

   @Scheduled(fixedRate = 28800000)
   public void MinGrowthTime_6() {
      logger.info("Start MinGrowthTime_6");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_6);
      logger.info("Finish MinGrowthTime_6");
   }

   @Scheduled(fixedRate = 57600000)
   public void MinGrowthTime_7() {
      logger.info("Start MinGrowthTime_7");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_7);
      logger.info("Finish MinGrowthTime_7");
   }

}
