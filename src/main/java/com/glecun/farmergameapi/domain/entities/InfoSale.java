package com.glecun.farmergameapi.domain.entities;

import java.util.Objects;

public class InfoSale {
    public final int nbFarmer;
    public final int nbTotalHarvestableSold;
    public final int nbHarvestableSold;
    public final boolean isEverythingSold;
    public final int revenue;
    public final int profit;

    public InfoSale(int nbFarmer, int nbTotalHarvestableSold, int nbHarvestableSold, boolean isEverythingSold, int revenue, int profit) {
        this.nbFarmer = nbFarmer;
        this.nbTotalHarvestableSold = nbTotalHarvestableSold;
        this.nbHarvestableSold = nbHarvestableSold;
        this.isEverythingSold = isEverythingSold;
        this.revenue = revenue;
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoSale infoSale = (InfoSale) o;
        return nbFarmer == infoSale.nbFarmer &&
                nbTotalHarvestableSold == infoSale.nbTotalHarvestableSold &&
                nbHarvestableSold == infoSale.nbHarvestableSold &&
                isEverythingSold == infoSale.isEverythingSold &&
                revenue == infoSale.revenue &&
                profit == infoSale.profit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nbFarmer, nbTotalHarvestableSold, nbHarvestableSold, isEverythingSold, revenue, profit);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InfoSale{");
        sb.append("nbFarmer=").append(nbFarmer);
        sb.append(", nbTotalHarvestableSold=").append(nbTotalHarvestableSold);
        sb.append(", nbHarvestableSold=").append(nbHarvestableSold);
        sb.append(", isEverythingSold=").append(isEverythingSold);
        sb.append(", revenue=").append(revenue);
        sb.append(", profit=").append(profit);
        sb.append('}');
        return sb.toString();
    }
}
