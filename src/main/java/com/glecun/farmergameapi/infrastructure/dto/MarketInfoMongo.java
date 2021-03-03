package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.MarketInfo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Document("marketInfo")
public class MarketInfoMongo {
    @Id
    public String id;
    public final List<OnSaleSeedMongo> onSaleSeeds;
    public final LocalDateTime marketTime;

    public MarketInfoMongo(String id, List<OnSaleSeedMongo> onSaleSeeds, LocalDateTime marketTime) {
        this.id = id;
        this.onSaleSeeds = onSaleSeeds;
        this.marketTime = marketTime;
    }

    public static MarketInfoMongo from(MarketInfo marketInfo) {
        return new MarketInfoMongo(
            marketInfo.id,
            marketInfo.onSaleSeeds.stream().map(OnSaleSeedMongo::from).collect(Collectors.toList()),
            marketInfo.marketTime
        );
    }

    public MarketInfo toMarketInfo() {
        return new MarketInfo(
              id,
              onSaleSeeds.stream().map(OnSaleSeedMongo::toOnSaleSeed).collect(Collectors.toList()),
              marketTime
        );
    }
}
