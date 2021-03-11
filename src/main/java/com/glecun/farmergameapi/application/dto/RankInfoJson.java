package com.glecun.farmergameapi.application.dto;

import com.glecun.farmergameapi.domain.entities.RankInfo;

public class RankInfoJson {
    public final int rank;
    public final String username;
    public final double profit;

    public RankInfoJson(int rank, String username, double profit) {
        this.rank = rank;
        this.username = username;
        this.profit = profit;
    }

    public static RankInfoJson from(RankInfo rankInfo) {
        return new RankInfoJson(rankInfo.rank, rankInfo.username, rankInfo.profit);
    }
}
