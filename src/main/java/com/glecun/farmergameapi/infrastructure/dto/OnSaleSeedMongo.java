package com.glecun.farmergameapi.infrastructure.dto;

import java.time.LocalDateTime;
import com.glecun.farmergameapi.domain.entities.OnSaleSeed;
import com.glecun.farmergameapi.domain.entities.SeedEnum;

public class OnSaleSeedMongo {
    public final SeedEnumMongo seedEnum;
    public final int buyPrice;
    public final int sellPrice;
    public final DemandMongo demand;
    public final LocalDateTime onSaleDate;
    public final LocalDateTime willBeSoldDate;

    public OnSaleSeedMongo(SeedEnumMongo seedEnum, int buyPrice, int sellPrice, DemandMongo demand, LocalDateTime onSaleDate, LocalDateTime willBeSoldDate) {
        this.seedEnum = seedEnum;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.demand = demand;
        this.onSaleDate = onSaleDate;
        this.willBeSoldDate = willBeSoldDate;
    }

    public OnSaleSeed toOnSaleSeed() {
        return OnSaleSeed.builder()
                .seedEnum(SeedEnum.values()[seedEnum.ordinal()])
                .sellPrice(sellPrice)
                .buyPrice(buyPrice)
                .demand(demand.toDemand())
                .onSaleDate(onSaleDate)
                .willBeSoldDate(willBeSoldDate)
                .build();
    }

    public static OnSaleSeedMongo from(OnSaleSeed onSaleSeed) {
        return new OnSaleSeedMongo(
                SeedEnumMongo.values()[onSaleSeed.seedEnum.ordinal()],
                onSaleSeed.buyPrice,
                onSaleSeed.sellPrice,
                DemandMongo.from(onSaleSeed.demand),
                onSaleSeed.onSaleDate,
                onSaleSeed.willBeSoldDate
        );
    }
}
