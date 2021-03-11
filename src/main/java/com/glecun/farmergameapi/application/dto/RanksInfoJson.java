package com.glecun.farmergameapi.application.dto;

import com.glecun.farmergameapi.domain.entities.RankInfo;
import com.glecun.farmergameapi.domain.entities.RanksInfo;

import java.util.List;
import java.util.stream.Collectors;

public class RanksInfoJson {
    public final List<RankInfoJson> allRanksInfo;
    public final RankInfoJson myRankInfo;
    public final List<RankInfoJson> betterNearMeRankInfo;
    public final List<RankInfoJson> worseAroundMeRankInfo;
    public final double nbAllFarmers;

    public RanksInfoJson(List<RankInfoJson> allRanksInfo, RankInfoJson myRankInfo, List<RankInfoJson> betterNearMeRankInfo, List<RankInfoJson> worseAroundMeRankInfo, double nbAllFarmers) {
        this.allRanksInfo = allRanksInfo;
        this.myRankInfo = myRankInfo;
        this.betterNearMeRankInfo = betterNearMeRankInfo;
        this.worseAroundMeRankInfo = worseAroundMeRankInfo;
        this.nbAllFarmers = nbAllFarmers;
    }

    public static RanksInfoJson from(RanksInfo ranksInfo) {
        return new RanksInfoJson(
                ranksInfo.allRanksInfo.stream().map(RankInfoJson::from).collect(Collectors.toList()),
                RankInfoJson.from(ranksInfo.myRankInfo),
                ranksInfo.betterNearMeRankInfo.stream().map(RankInfoJson::from).collect(Collectors.toList()),
                ranksInfo.worseAroundMeRankInfo.stream().map(RankInfoJson::from).collect(Collectors.toList()),
                ranksInfo.nbAllFarmers
        );
    }
}
