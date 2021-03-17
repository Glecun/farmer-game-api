package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.ProfitByTier;
import com.glecun.farmergameapi.domain.entities.TierEnum;

public class ProfitByTierMongo {

    public final TierEnum tier;
    public final double profit;

    public ProfitByTierMongo(TierEnum tier, double profit) {
        this.tier = tier;
        this.profit = profit;
    }

    public static ProfitByTierMongo from(ProfitByTier profitByTier) {
        return new ProfitByTierMongo(profitByTier.tier, profitByTier.profit);
    }

    public ProfitByTier toProfitByTier() {
        return new ProfitByTier(tier, profit);
    }
}
