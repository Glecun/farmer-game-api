package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.GrowthTime;
import com.glecun.farmergameapi.domain.entities.PriceRange;
import com.glecun.farmergameapi.domain.entities.Seed;

public class SeedMongo {
    public final GrowthTimeMongo growthTime;
    public final PriceRangeMongo buyPrice;
    public final PriceRangeMongo sellPrice;

    public SeedMongo(GrowthTimeMongo growthTime, PriceRangeMongo buyPrice, PriceRangeMongo sellPrice) {
        this.growthTime = growthTime;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
}
