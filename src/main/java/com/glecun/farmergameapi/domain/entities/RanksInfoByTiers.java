package com.glecun.farmergameapi.domain.entities;

import java.util.List;
import java.util.Objects;

public class RanksInfoByTiers {
    public final RanksInfo globalRanksInfo;
    public final List<RankInfoByTier> rankInfoByTiers;

    public RanksInfoByTiers(RanksInfo globalRanksInfo, List<RankInfoByTier> rankInfoByTiers) {
        this.globalRanksInfo = globalRanksInfo;
        this.rankInfoByTiers = rankInfoByTiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RanksInfoByTiers that = (RanksInfoByTiers) o;
        return Objects.equals(globalRanksInfo, that.globalRanksInfo) &&
                Objects.equals(rankInfoByTiers, that.rankInfoByTiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(globalRanksInfo, rankInfoByTiers);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RanksInfoByTiers{");
        sb.append("globalRanksInfo=").append(globalRanksInfo);
        sb.append(", rankInfoByTiers=").append(rankInfoByTiers);
        sb.append('}');
        return sb.toString();
    }
}
