package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.ProfitsByTiers;

import java.util.List;
import java.util.stream.Collectors;

public class ProfitsByTiersMongo {

    public final double globalProfit;
    public final List<ProfitByTierMongo> profitByTier;

    public ProfitsByTiersMongo(double globalProfit, List<ProfitByTierMongo> profitByTier) {
        this.globalProfit = globalProfit;
        this.profitByTier = profitByTier;
    }

    public static ProfitsByTiersMongo from(ProfitsByTiers profits) {
        return new ProfitsByTiersMongo(
                profits.globalProfit,
                profits.profitByTier.stream().map(ProfitByTierMongo::from).collect(Collectors.toList())
        );
    }

    public ProfitsByTiers toProfitsByTiers() {
        return new ProfitsByTiers(
                globalProfit,
                profitByTier.stream().map(ProfitByTierMongo::toProfitByTier).collect(Collectors.toList())
        );
    }
}
