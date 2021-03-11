package com.glecun.farmergameapi.domain.entities;

import java.util.Objects;

public class RankInfo {
    public final int rank;
    public final String username;
    public final double profit;

    public RankInfo(int rank, String username, double profit) {
        this.rank = rank;
        this.username = username;
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RankInfo rankInfo = (RankInfo) o;
        return rank == rankInfo.rank &&
                Double.compare(rankInfo.profit, profit) == 0 &&
                Objects.equals(username, rankInfo.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, username, profit);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RankInfo{");
        sb.append("rank=").append(rank);
        sb.append(", username='").append(username).append('\'');
        sb.append(", profit=").append(profit);
        sb.append('}');
        return sb.toString();
    }
}
