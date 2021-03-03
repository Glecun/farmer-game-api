package com.glecun.farmergameapi.domain;

import com.glecun.farmergameapi.domain.entities.*;
import com.glecun.farmergameapi.domain.port.MarketInfoPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GenerateMarketInfos {

    private final MarketInfoPort marketInfoPort;
    private final GetCurrentMarketInfo getCurrentMarketInfo;

    @Autowired
    public GenerateMarketInfos(MarketInfoPort marketInfoPort, GetCurrentMarketInfo getCurrentMarketInfo) {
        this.marketInfoPort = marketInfoPort;
        this.getCurrentMarketInfo = getCurrentMarketInfo;
    }

    public void execute(GrowthTime growthTime){
        List<OnSaleSeed> onSaleSeedsToUpdate = Arrays.stream(SeedEnum.values())
                .filter(seedEnum -> seedEnum.seed.hasGrowthTime(growthTime))
                .map(seedEnum ->
                        OnSaleSeed.builder()
                            .seedEnum(seedEnum)
                            .buyPrice(seedEnum.seed.randomizeBuyPrice())
                            .sellPrice(seedEnum.seed.randomizeSellPrice())
                            .demand(randomizeDemand())
                            .onSaleDate(LocalDateTime.now())
                            .willBeSoldDate(LocalDateTime.now().plusSeconds(Double.valueOf(growthTime.growthTime * 60).longValue()))
                            .build()
                )
                .collect(Collectors.toList());

        List<OnSaleSeed> onSaleSeedsToKeep = getCurrentMarketInfo.execute()
                .map(marketInfo -> marketInfo.onSaleSeeds)
                .stream().flatMap(Collection::stream)
                .filter(onSaleSeed -> !onSaleSeed.seedEnum.seed.hasGrowthTime(growthTime))
                .collect(Collectors.toList());

        List<OnSaleSeed> onSaleSeeds = Stream.concat(onSaleSeedsToUpdate.stream(), onSaleSeedsToKeep.stream()).collect(Collectors.toList());

        marketInfoPort.save(new MarketInfo(null, onSaleSeeds, LocalDateTime.now()));
    }

    //TODO: take care of User who can sell it
    private Demand randomizeDemand() {
        return new Demand(DemandType.SmallDemand, 10);
    }
}