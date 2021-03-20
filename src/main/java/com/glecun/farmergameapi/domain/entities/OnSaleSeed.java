package com.glecun.farmergameapi.domain.entities;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class OnSaleSeed {
    public final SeedEnum seedEnum;
    public final int buyPrice;
    public final int sellPrice;
    public final Demand demand;
    public final LocalDateTime onSaleDate;
    public final LocalDateTime willBeSoldDate;

    private OnSaleSeed(SeedEnum seedEnum, int buyPrice, int sellPrice, Demand demand, LocalDateTime onSaleDate, LocalDateTime willBeSoldDate) {
        this.seedEnum = seedEnum;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.demand = demand;
        this.onSaleDate = onSaleDate;
        this.willBeSoldDate = willBeSoldDate;
    }

    public long timeLeft() {
        if(onSaleDate != null){
            return LocalDateTime.now(ZoneOffset.UTC).until(onSaleDate.plusSeconds(Double.valueOf(seedEnum.seed.growthTime.minGrowthTime * 60).longValue()), ChronoUnit.SECONDS);
        }
        return 0;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnSaleSeed that = (OnSaleSeed) o;
        return buyPrice == that.buyPrice && sellPrice == that.sellPrice && seedEnum == that.seedEnum && Objects.equals(demand, that.demand) && Objects.equals(onSaleDate, that.onSaleDate) && Objects.equals(willBeSoldDate, that.willBeSoldDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seedEnum, buyPrice, sellPrice, demand, onSaleDate, willBeSoldDate);
    }

    @Override
    public String toString() {
        return "OnSaleSeed{" +
              "seedEnum=" + seedEnum +
              ", buyPrice=" + buyPrice +
              ", sellPrice=" + sellPrice +
              ", demand=" + demand +
              ", onSaleDate=" + onSaleDate +
              ", willBeSoldDate=" + willBeSoldDate +
              '}';
    }

    public boolean canBeSell() {
        return willBeSoldDate.isBefore(LocalDateTime.now(ZoneOffset.UTC).plusSeconds(20));
    }

    public OnSaleSeed SetNbDemand(int nbDemand) {
        Demand demand = new Demand(this.demand.demandType, nbDemand);
        return new OnSaleSeed(seedEnum, buyPrice, sellPrice, demand, onSaleDate, willBeSoldDate);
    }

    public static class Builder {
        private SeedEnum seedEnum;
        private int buyPrice;
        private int sellPrice;
        private Demand demand;
        private LocalDateTime onSaleDate;
        private LocalDateTime willBeSoldDate;

        public Builder seedEnum(SeedEnum seedEnum) {
            this.seedEnum = seedEnum;
            return this;
        }

        public Builder buyPrice(int buyPrice) {
            this.buyPrice = buyPrice;
            return this;
        }

        public Builder sellPrice(int sellPrice) {
            this.sellPrice = sellPrice;
            return this;
        }

        public Builder demand(Demand demand) {
            this.demand = demand;
            return this;
        }

        public Builder onSaleDate(LocalDateTime onSaleDate) {
            this.onSaleDate = onSaleDate;
            return this;
        }

        public Builder willBeSoldDate(LocalDateTime willBeSoldDate) {
            this.willBeSoldDate = willBeSoldDate;
            return this;
        }

        public OnSaleSeed build(){
            return new OnSaleSeed(seedEnum, buyPrice, sellPrice, demand, onSaleDate, willBeSoldDate);
        }
    }
}
