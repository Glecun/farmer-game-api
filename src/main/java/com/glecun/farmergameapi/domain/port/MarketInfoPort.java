package com.glecun.farmergameapi.domain.port;

import com.glecun.farmergameapi.domain.entities.MarketInfo;

import java.util.List;

public interface MarketInfoPort {

    List<MarketInfo> getMarketInfos();

    void save(MarketInfo marketInfo);

    void deleteAll(List<MarketInfo> marketInfos);
}
