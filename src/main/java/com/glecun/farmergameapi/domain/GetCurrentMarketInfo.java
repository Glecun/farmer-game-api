package com.glecun.farmergameapi.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.glecun.farmergameapi.domain.entities.MarketInfo;
import com.glecun.farmergameapi.domain.port.MarketInfoPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCurrentMarketInfo {

    private final MarketInfoPort marketInfoPort;

    @Autowired
    public GetCurrentMarketInfo(MarketInfoPort marketInfoPort) {
        this.marketInfoPort = marketInfoPort;
    }

    public Optional<MarketInfo> execute() {
        var marketInfos = marketInfoPort.getMarketInfos();
        var maybeCurrentMarketInfo = marketInfos.stream()
              .max(Comparator.comparing(marketInfo -> marketInfo.marketTime));

        maybeCurrentMarketInfo.ifPresent(marketInfo -> removeOldMarketInfo(marketInfos, marketInfo));

        return maybeCurrentMarketInfo;
    }

    private void removeOldMarketInfo(List<MarketInfo> marketInfos, MarketInfo currentMarketInfo) {
        var oldMarketInfos = marketInfos.stream()
              .filter(marketInfo -> !marketInfo.equals(currentMarketInfo))
              .collect(Collectors.toList());
        marketInfoPort.deleteAll(oldMarketInfos);
    }
}
