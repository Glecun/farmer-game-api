package com.glecun.farmergameapi.domain.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class MarketInfo {

    public final String id;
    public final List<OnSaleSeed> onSaleSeeds;
    public final LocalDateTime marketTime;

    public MarketInfo(String id, List<OnSaleSeed> onSaleSeeds, LocalDateTime marketTime) {
        this.id = id;
        this.onSaleSeeds = onSaleSeeds;
        this.marketTime = marketTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketInfo that = (MarketInfo) o;
        return Objects.equals(onSaleSeeds, that.onSaleSeeds) && Objects.equals(marketTime, that.marketTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(onSaleSeeds, marketTime);
    }

    @Override
    public String toString() {
        return "MarketInfo{" +
              "onSaleSeeds=" + onSaleSeeds +
              ", marketTime=" + marketTime +
              '}';
    }
}
