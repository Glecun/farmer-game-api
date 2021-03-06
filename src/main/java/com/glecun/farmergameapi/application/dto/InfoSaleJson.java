package com.glecun.farmergameapi.application.dto;

import com.glecun.farmergameapi.domain.entities.InfoSale;

public class InfoSaleJson {
    public final int nbFarmer;
    public final long nbTotalFarmer;
    public final int nbTotalHarvestableSold;
    public final int nbHarvestableSold;
    public final boolean isEverythingSold;
    public final int revenue;
    public final int profit;

    public InfoSaleJson(int nbFarmer, long nbTotalFarmer, int nbTotalHarvestableSold, int nbHarvestableSold, boolean isEverythingSold, int revenue, int profit) {
        this.nbFarmer = nbFarmer;
        this.nbTotalFarmer = nbTotalFarmer;
        this.nbTotalHarvestableSold = nbTotalHarvestableSold;
        this.nbHarvestableSold = nbHarvestableSold;
        this.isEverythingSold = isEverythingSold;
        this.revenue = revenue;
        this.profit = profit;
    }

    public static InfoSaleJson from(InfoSale infoSale) {
        return new InfoSaleJson(
                infoSale.nbFarmer,
                infoSale.nbTotalFarmer,
                infoSale.nbTotalHarvestableSold,
                infoSale.nbHarvestableSold,
                infoSale.isEverythingSold,
                infoSale.revenue,
                infoSale.profit
        );
    }

    public InfoSale toInfoSale() {
        return new InfoSale(nbFarmer, nbTotalFarmer, nbTotalHarvestableSold, nbHarvestableSold, isEverythingSold, revenue, profit);
    }
}
