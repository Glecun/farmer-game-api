package com.glecun.farmergameapi.domain.entities;

import java.util.Objects;

public class Seed {
    public final GrowthTime growthTime;
    public final PriceRange buyPrice;
    public final PriceRange sellPrice;

    public Seed(GrowthTime growthTime, PriceRange buyPrice, PriceRange sellPrice) {
        this.growthTime = growthTime;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public boolean hasGrowthTime(GrowthTime growthTime) {
        return this.growthTime.equals(growthTime);
    }

    public int randomizeBuyPrice() {
        return buyPrice.randomizeInRange();
    }

    public int randomizeSellPrice(Demand demand) {
        return sellPrice.randomizeInRangeAccordingTo(demand);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seed seed = (Seed) o;
        return growthTime == seed.growthTime &&
                Objects.equals(buyPrice, seed.buyPrice) &&
                Objects.equals(sellPrice, seed.sellPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(growthTime, buyPrice, sellPrice);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Seed{");
        sb.append("growthTime=").append(growthTime);
        sb.append(", buyPrice=").append(buyPrice);
        sb.append(", sellPrice=").append(sellPrice);
        sb.append('}');
        return sb.toString();
    }
}
