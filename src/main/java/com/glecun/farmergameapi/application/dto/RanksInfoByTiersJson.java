package com.glecun.farmergameapi.application.dto;

import com.glecun.farmergameapi.domain.entities.RankInfoByTier;
import com.glecun.farmergameapi.domain.entities.RanksInfo;
import com.glecun.farmergameapi.domain.entities.RanksInfoByTiers;

import java.util.List;
import java.util.stream.Collectors;

public class RanksInfoByTiersJson {
    public final RanksInfo globalRanksInfo;
    public final List<RankInfoByTierJson> rankInfoByTiers;

    public RanksInfoByTiersJson(RanksInfo globalRanksInfo, List<RankInfoByTierJson> rankInfoByTiers) {
        this.globalRanksInfo = globalRanksInfo;
        this.rankInfoByTiers = rankInfoByTiers;
    }

    public static RanksInfoByTiersJson from(RanksInfoByTiers ranksInfoByTiers) {
        return new RanksInfoByTiersJson(
                ranksInfoByTiers.globalRanksInfo,
                ranksInfoByTiers.rankInfoByTiers.stream().map(RankInfoByTierJson::from).collect(Collectors.toList())
        );
    }
}
