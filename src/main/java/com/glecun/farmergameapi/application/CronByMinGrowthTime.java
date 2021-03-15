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

   @Scheduled(fixedRate = 40000)
   public void MinGrowthTime_2() {
      logger.info("Start MinGrowthTime_2");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_2);
      logger.info("Finish MinGrowthTime_2");
   }

   @Scheduled(fixedRate = 200000)
   public void MinGrowthTime_3() {
      logger.info("Start MinGrowthTime_3");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_3);
      logger.info("Finish MinGrowthTime_3");
   }

   @Scheduled(fixedRate = 400000)
   public void MinGrowthTime_4() {
      logger.info("Start MinGrowthTime_4");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_4);
      logger.info("Finish MinGrowthTime_4");
   }

   @Scheduled(fixedRate = 800000)
   public void MinGrowthTime_5() {
      logger.info("Start MinGrowthTime_5");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_5);
      logger.info("Finish MinGrowthTime_5");
   }

   @Scheduled(fixedRate = 1600000)
   public void MinGrowthTime_6() {
      logger.info("Start MinGrowthTime_6");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_6);
      logger.info("Finish MinGrowthTime_6");
   }

   @Scheduled(fixedRate = 2400000)
   public void MinGrowthTime_7() {
      logger.info("Start MinGrowthTime_7");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_7);
      logger.info("Finish MinGrowthTime_7");
   }

   @Scheduled(fixedRate = 4800000)
   public void MinGrowthTime_8() {
      logger.info("Start MinGrowthTime_8");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_8);
      logger.info("Finish MinGrowthTime_8");
   }

   @Scheduled(fixedRate = 7200000)
   public void MinGrowthTime_9() {
      logger.info("Start MinGrowthTime_9");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_9);
      logger.info("Finish MinGrowthTime_9");
   }

   @Scheduled(fixedRate = 12000000)
   public void MinGrowthTime_10() {
      logger.info("Start MinGrowthTime_10");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_10);
      logger.info("Finish MinGrowthTime_10");
   }

   @Scheduled(fixedRate = 24000000)
   public void MinGrowthTime_11() {
      logger.info("Start MinGrowthTime_11");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_11);
      logger.info("Finish MinGrowthTime_11");
   }

   @Scheduled(fixedRate = 28800000)
   public void MinGrowthTime_12() {
      logger.info("Start MinGrowthTime_12");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_12);
      logger.info("Finish MinGrowthTime_12");
   }

   @Scheduled(fixedRate = 57600000)
   public void MinGrowthTime_13() {
      logger.info("Start MinGrowthTime_13");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_13);
      logger.info("Finish MinGrowthTime_13");
   }

   @Scheduled(fixedRate = 86400000)
   public void MinGrowthTime_14() {
      logger.info("Start MinGrowthTime_14");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_14);
      logger.info("Finish MinGrowthTime_14");
   }

   @Scheduled(fixedRate = 115200000)
   public void MinGrowthTime_15() {
      logger.info("Start MinGrowthTime_15");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_15);
      logger.info("Finish MinGrowthTime_15");
   }

   @Scheduled(fixedRate = 230400000)
   public void MinGrowthTime_16() {
      logger.info("Start MinGrowthTime_16");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_16);
      logger.info("Finish MinGrowthTime_16");
   }

   @Scheduled(fixedRate = 403200000)
   public void MinGrowthTime_17() {
      logger.info("Start MinGrowthTime_17");
      applicationDomain.generateMarketInfos(GrowthTime.GROWTH_TIME_17);
      logger.info("Finish MinGrowthTime_17");
   }
}
