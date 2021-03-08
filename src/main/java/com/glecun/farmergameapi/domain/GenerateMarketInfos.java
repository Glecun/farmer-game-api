package com.glecun.farmergameapi.domain;

import com.glecun.farmergameapi.domain.entities.*;
import com.glecun.farmergameapi.domain.port.MarketInfoPort;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.glecun.farmergameapi.domain.ApplicationDomain.NB_OF_FAKE_USERS;
import static com.glecun.farmergameapi.domain.ApplicationDomain.NB_OF_ZONE;

@Service
public class GenerateMarketInfos {

    private final MarketInfoPort marketInfoPort;
    private final GetCurrentMarketInfo getCurrentMarketInfo;
    private final UserInfoPort userInfoPort;

    @Autowired
    public GenerateMarketInfos(MarketInfoPort marketInfoPort, GetCurrentMarketInfo getCurrentMarketInfo, UserInfoPort userInfoPort) {
        this.marketInfoPort = marketInfoPort;
        this.getCurrentMarketInfo = getCurrentMarketInfo;
        this.userInfoPort = userInfoPort;
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
                            .onSaleDate(LocalDateTime.now(ZoneOffset.UTC))
                            .willBeSoldDate(LocalDateTime.now(ZoneOffset.UTC).plusSeconds(Double.valueOf(growthTime.growthTime * 60).longValue()))
                            .build()
                )
                .collect(Collectors.toList());

        List<OnSaleSeed> onSaleSeedsToKeep = getCurrentMarketInfo.execute()
                .map(marketInfo -> marketInfo.onSaleSeeds)
                .stream().flatMap(Collection::stream)
                .filter(onSaleSeed -> !onSaleSeed.seedEnum.seed.hasGrowthTime(growthTime))
                .collect(Collectors.toList());

        List<OnSaleSeed> onSaleSeeds = Stream.concat(onSaleSeedsToUpdate.stream(), onSaleSeedsToKeep.stream()).collect(Collectors.toList());

        marketInfoPort.save(new MarketInfo(null, onSaleSeeds, LocalDateTime.now(ZoneOffset.UTC)));
    }

    Demand randomizeDemand() {
        var demandTypeList = Arrays.stream(DemandType.values()).collect(Collectors.toList());
        DemandType randomDemandType = demandTypeList.stream().skip((int) (demandTypeList.size() * Math.random())).findAny().orElseThrow();
        Integer usersZoneCapacity = userInfoPort.findAll().stream().map(userInfo -> NB_OF_ZONE).reduce(0, Integer::sum);
        Integer fakePlayersZoneCapacity = IntStream.range(0, new Random().nextInt(NB_OF_FAKE_USERS + 1)).map(operand -> NB_OF_ZONE).reduce(0, Integer::sum);
        return new Demand(randomDemandType,  Math.round((usersZoneCapacity+fakePlayersZoneCapacity) * ((float)randomDemandType.percentOfNbZones/100)));
    }

}