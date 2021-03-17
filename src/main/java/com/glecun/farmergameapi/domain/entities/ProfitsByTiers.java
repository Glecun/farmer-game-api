package com.glecun.farmergameapi.domain.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProfitsByTiers {

    public final double globalProfit;
    public final List<ProfitByTier> profitByTier;

    public ProfitsByTiers(double globalProfit, List<ProfitByTier> profitByTier) {
        this.globalProfit = globalProfit;
        this.profitByTier = profitByTier;
    }

    public static ProfitsByTiers create() {
        var profitByTier = Arrays.stream(TierEnum.values())
                .map(tierEnum -> new ProfitByTier(tierEnum, 0))
                .collect(Collectors.toList());
        return new ProfitsByTiers(0, profitByTier);
    }

    public ProfitsByTiers addProfit(int amount, TierEnum tierOfZone) {
        List<ProfitByTier> profitByTiers = profitByTier.stream()
                .map(aProfitByTier -> {
                    if (aProfitByTier.tier.equals(tierOfZone)) {
                        return new ProfitByTier(aProfitByTier.tier, aProfitByTier.profit + amount);
                    }
                    return aProfitByTier;
                })
                .collect(Collectors.toList());
        return new ProfitsByTiers(globalProfit + amount, profitByTiers);
    }

    public double getProfit(TierEnum tierEnum) {
        return profitByTier.stream()
                .filter(aProfitByTier -> aProfitByTier.tier.equals(tierEnum))
                .map(aProfitByTier -> aProfitByTier.profit)
                .findFirst().orElseThrow();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfitsByTiers that = (ProfitsByTiers) o;
        return Double.compare(that.globalProfit, globalProfit) == 0 &&
                Objects.equals(profitByTier, that.profitByTier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(globalProfit, profitByTier);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProfitsByTiers{");
        sb.append("globalProfit=").append(globalProfit);
        sb.append(", profitByTier=").append(profitByTier);
        sb.append('}');
        return sb.toString();
    }
}
