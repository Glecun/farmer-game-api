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

    @Scheduled(fixedRate = 40000, initialDelay = 15000)
    public void GrowthTime_2() {
        logger.info("Start GrowthTime_2");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_2);
        logger.info("Finish GrowthTime_2");
    }

    @Scheduled(fixedRate = 200000, initialDelay = 95000)
    public void GrowthTime_3() {
        logger.info("Start GrowthTime_3");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_3);
        logger.info("Finish GrowthTime_3");
    }

    @Scheduled(fixedRate = 400000, initialDelay = 195000)
    public void GrowthTime_4() {
        logger.info("Start GrowthTime_4");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_4);
        logger.info("Finish GrowthTime_4");
    }

    @Scheduled(fixedRate = 800000, initialDelay = 395000)
    public void GrowthTime_5() {
        logger.info("Start GrowthTime_5");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_5);
        logger.info("Finish GrowthTime_5");
    }

    @Scheduled(fixedRate = 1600000, initialDelay = 795000)
    public void GrowthTime_6() {
        logger.info("Start GrowthTime_6");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_6);
        logger.info("Finish GrowthTime_6");
    }

    @Scheduled(fixedRate = 2400000, initialDelay = 1195000)
    public void GrowthTime_7() {
        logger.info("Start GrowthTime_7");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_7);
        logger.info("Finish GrowthTime_7");
    }

    @Scheduled(fixedRate = 4800000, initialDelay = 2395000)
    public void GrowthTime_8() {
        logger.info("Start GrowthTime_8");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_8);
        logger.info("Finish GrowthTime_8");
    }

    @Scheduled(fixedRate = 7200000, initialDelay = 3595000)
    public void GrowthTime_9() {
        logger.info("Start GrowthTime_9");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_9);
        logger.info("Finish GrowthTime_9");
    }

    @Scheduled(fixedRate = 12000000, initialDelay = 5995000)
    public void GrowthTime_10() {
        logger.info("Start GrowthTime_10");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_10);
        logger.info("Finish GrowthTime_10");
    }

    @Scheduled(fixedRate = 24000000, initialDelay = 11995000)
    public void GrowthTime_11() {
        logger.info("Start GrowthTime_11");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_11);
        logger.info("Finish GrowthTime_11");
    }

    @Scheduled(fixedRate = 28800000, initialDelay = 14395000)
    public void GrowthTime_12() {
        logger.info("Start GrowthTime_12");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_12);
        logger.info("Finish GrowthTime_12");
    }

    @Scheduled(fixedRate = 57600000, initialDelay = 28795000)
    public void GrowthTime_13() {
        logger.info("Start GrowthTime_13");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_13);
        logger.info("Finish GrowthTime_13");
    }

    @Scheduled(fixedRate = 86400000, initialDelay = 43195000)
    public void GrowthTime_14() {
        logger.info("Start GrowthTime_14");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_14);
        logger.info("Finish GrowthTime_14");
    }

    @Scheduled(fixedRate = 115200000, initialDelay = 57595000)
    public void GrowthTime_15() {
        logger.info("Start GrowthTime_15");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_15);
        logger.info("Finish GrowthTime_15");
    }

    @Scheduled(fixedRate = 230400000, initialDelay = 115195000)
    public void GrowthTime_16() {
        logger.info("Start GrowthTime_16");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_16);
        logger.info("Finish GrowthTime_16");
    }

    @Scheduled(fixedRate = 403200000, initialDelay = 201595000)
    public void GrowthTime_17() {
        logger.info("Start GrowthTime_17");
        applicationDomain.resolveSales(GrowthTime.GROWTH_TIME_17);
        logger.info("Finish GrowthTime_17");
    }

}
