package com.glecun.farmergameapi.application.dto;

import com.glecun.farmergameapi.domain.entities.RankInfoByTier;
import com.glecun.farmergameapi.domain.entities.RanksInfo;
import com.glecun.farmergameapi.domain.entities.TierEnum;

public class RankInfoByTierJson {

    public final TierEnum tier;
    public final RanksInfoJson ranksInfo;

    public RankInfoByTierJson(TierEnum tier, RanksInfoJson ranksInfo) {
        this.tier = tier;
        this.ranksInfo = ranksInfo;
    }

    public static RankInfoByTierJson from(RankInfoByTier rankInfoByTier) {
        return new RankInfoByTierJson(rankInfoByTier.tier, RanksInfoJson.from(rankInfoByTier.ranksInfo));
    }
}
