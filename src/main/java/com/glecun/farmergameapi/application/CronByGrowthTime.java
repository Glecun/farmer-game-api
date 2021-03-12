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

    //initialDelay = (growthTime - minGrowthTime) - 5s

    @Scheduled(fixedRate = 20000, initialDelay = 5000)
    public void firstGrowthTime() {
        logger.info("Start firstGrowthTime");
        applicationDomain.resolveSales(GrowthTime.FIRST_GROWTH_TIME);
    }

    @Scheduled(fixedRate = 120000, initialDelay = 55000)
    public void secondGrowthTime() {
        logger.info("Start secondGrowthTime");
        applicationDomain.resolveSales(GrowthTime.SECOND_GROWTH_TIME);
    }

}
