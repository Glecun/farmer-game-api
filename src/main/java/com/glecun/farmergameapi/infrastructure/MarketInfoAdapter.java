package com.glecun.farmergameapi.infrastructure;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.glecun.farmergameapi.domain.entities.MarketInfo;
import com.glecun.farmergameapi.domain.port.MarketInfoPort;
import com.glecun.farmergameapi.infrastructure.dto.MarketInfoMongo;
import com.glecun.farmergameapi.infrastructure.repo.MarketInfoRepository;

@Repository
public class MarketInfoAdapter implements MarketInfoPort {

    private final MarketInfoRepository marketInfoRepository;

    @Autowired
    public MarketInfoAdapter(MarketInfoRepository marketInfoRepository) {
        this.marketInfoRepository = marketInfoRepository;
    }

    @Override
    public List<MarketInfo> getMarketInfos() {
        return marketInfoRepository.findAll().stream()
                .map(MarketInfoMongo::toMarketInfo)
                .collect(Collectors.toList());
    }

    @Override
    public void save(MarketInfo marketInfo) {
        marketInfoRepository.save(MarketInfoMongo.from(marketInfo));
    }

    @Override
    public void deleteAll(List<MarketInfo> marketInfos) {
        marketInfoRepository.deleteAll(marketInfos.stream().map(MarketInfoMongo::from).collect(Collectors.toList()));
    }
}
