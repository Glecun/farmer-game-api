package com.glecun.farmergameapi.domain.entities;

import java.util.Objects;

public class ProfitByTier {
    public final TierEnum tier;
    public final double profit;

    public ProfitByTier(TierEnum tier, double profit) {
        this.tier = tier;
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfitByTier that = (ProfitByTier) o;
        return Double.compare(that.profit, profit) == 0 &&
                tier == that.tier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tier, profit);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProfitByTier{");
        sb.append("tier=").append(tier);
        sb.append(", profit=").append(profit);
        sb.append('}');
        return sb.toString();
    }
}
