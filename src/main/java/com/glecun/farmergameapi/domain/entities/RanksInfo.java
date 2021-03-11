package com.glecun.farmergameapi.domain.entities;

import java.util.List;
import java.util.Objects;

public class RanksInfo {

    public final List<RankInfo> allRanksInfo;
    public final RankInfo myRankInfo;
    public final List<RankInfo> betterNearMeRankInfo;
    public final List<RankInfo> worseAroundMeRankInfo;
    public final double nbAllFarmers;

    public RanksInfo(List<RankInfo> allRanksInfo, RankInfo myRankInfo, List<RankInfo> betterNearMeRankInfo, List<RankInfo> worseAroundMeRankInfo, double nbAllFarmers) {
        this.allRanksInfo = allRanksInfo;
        this.myRankInfo = myRankInfo;
        this.betterNearMeRankInfo = betterNearMeRankInfo;
        this.worseAroundMeRankInfo = worseAroundMeRankInfo;
        this.nbAllFarmers = nbAllFarmers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RanksInfo ranksInfo = (RanksInfo) o;
        return Double.compare(ranksInfo.nbAllFarmers, nbAllFarmers) == 0 &&
                Objects.equals(allRanksInfo, ranksInfo.allRanksInfo) &&
                Objects.equals(myRankInfo, ranksInfo.myRankInfo) &&
                Objects.equals(betterNearMeRankInfo, ranksInfo.betterNearMeRankInfo) &&
                Objects.equals(worseAroundMeRankInfo, ranksInfo.worseAroundMeRankInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(allRanksInfo, myRankInfo, betterNearMeRankInfo, worseAroundMeRankInfo, nbAllFarmers);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RanksInfo{");
        sb.append("allRanksInfo=").append(allRanksInfo);
        sb.append(", myRankInfo=").append(myRankInfo);
        sb.append(", betterNearMeRankInfo=").append(betterNearMeRankInfo);
        sb.append(", worseAroundMeRankInfo=").append(worseAroundMeRankInfo);
        sb.append(", nbAllFarmers=").append(nbAllFarmers);
        sb.append('}');
        return sb.toString();
    }
}
