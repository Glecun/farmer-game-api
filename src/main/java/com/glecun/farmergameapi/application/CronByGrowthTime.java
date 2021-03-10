package com.glecun.farmergameapi.application;

import com.glecun.farmergameapi.domain.ApplicationDomain;
import com.glecun.farmergameapi.domain.entities.GrowthTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronByGrowthTime {

    private final ApplicationDomain applicationDomain;

    @Autowired
    public CronByGrowthTime(ApplicationDomain applicationDomain) {
        this.applicationDomain = applicationDomain;
    }

    //initialDelay = (growthTime - minGrowthTime) - 5s

    @Scheduled(fixedRate = 20000, initialDelay = 5000)
    public void firstGrowthTime() {
        applicationDomain.resolveSales(GrowthTime.FIRST_GROWTH_TIME);
    }

    @Scheduled(fixedRate = 120000, initialDelay = 55000)
    public void secondGrowthTime() {
        applicationDomain.resolveSales(GrowthTime.SECOND_GROWTH_TIME);
    }

}
