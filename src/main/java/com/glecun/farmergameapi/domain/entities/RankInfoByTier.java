package com.glecun.farmergameapi.domain.entities;

import java.util.Objects;

public class RankInfoByTier {
    public final TierEnum tier;
    public final RanksInfo ranksInfo;

    public RankInfoByTier(TierEnum tier, RanksInfo ranksInfo) {
        this.tier = tier;
        this.ranksInfo = ranksInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RankInfoByTier that = (RankInfoByTier) o;
        return tier == that.tier &&
                Objects.equals(ranksInfo, that.ranksInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tier, ranksInfo);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RankInfoByTier{");
        sb.append("tier=").append(tier);
        sb.append(", ranksInfo=").append(ranksInfo);
        sb.append('}');
        return sb.toString();
    }
}
