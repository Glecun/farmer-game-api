package com.glecun.farmergameapi.application;

import com.glecun.farmergameapi.domain.ApplicationDomain;
import com.glecun.farmergameapi.domain.entities.GrowthTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronByGrowthTime {

    Logger logger = LoggerFactory.getLogger(CronByGrowthTime.class);

    private final ApplicationDomain applicationDomain;

    @Autowired
    public CronByGrowthTime(ApplicationDomain applicationDomain) {
        this.applicationDomain = applicationDomain;
    }

    //initialDelay= (growthTime - minGrowthTime) - 5s

    @Scheduled(fixedRate = 20000, initialDelay = 5000)
    public void GrowthTime_1() {
        logger.info("Start GrowthTime_1");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_1);
        logger.info("Finish GrowthTime_1");
    }

    @Scheduled(fixedRate = 200000, initialDelay = 95000)
    public void GrowthTime_2() {
        logger.info("Start GrowthTime_2");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_2);
        logger.info("Finish GrowthTime_2");
    }

    @Scheduled(fixedRate = 1200000, initialDelay = 595000)
    public void GrowthTime_3() {
        logger.info("Start GrowthTime_3");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_3);
        logger.info("Finish GrowthTime_3");
    }

    @Scheduled(fixedRate = 2400000, initialDelay = 1195000)
    public void GrowthTime_4() {
        logger.info("Start GrowthTime_4");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_4);
        logger.info("Finish GrowthTime_4");
    }

    @Scheduled(fixedRate = 7200000, initialDelay = 3595000)
    public void GrowthTime_5() {
        logger.info("Start GrowthTime_5");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_5);
        logger.info("Finish GrowthTime_5");
    }

    @Scheduled(fixedRate = 28800000, initialDelay = 14395000)
    public void GrowthTime_6() {
        logger.info("Start GrowthTime_6");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_6);
        logger.info("Finish GrowthTime_6");
    }

    @Scheduled(fixedRate = 57600000, initialDelay = 28795000)
    public void GrowthTime_7() {
        logger.info("Start GrowthTime_7");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_7);
        logger.info("Finish GrowthTime_7");
    }

}
