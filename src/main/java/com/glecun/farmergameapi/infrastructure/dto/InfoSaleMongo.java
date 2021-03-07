package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.application.dto.InfoSaleJson;
import com.glecun.farmergameapi.domain.entities.InfoSale;

public class InfoSaleMongo {
    public final int nbFarmer;
    public final int nbTotalHarvestableSold;
    public final int nbHarvestableSold;
    public final boolean isEverythingSold;
    public final int revenue;
    public final int profit;

    public InfoSaleMongo(int nbFarmer, int nbTotalHarvestableSold, int nbHarvestableSold, boolean isEverythingSold, int revenue, int profit) {
        this.nbFarmer = nbFarmer;
        this.nbTotalHarvestableSold = nbTotalHarvestableSold;
        this.nbHarvestableSold = nbHarvestableSold;
        this.isEverythingSold = isEverythingSold;
        this.revenue = revenue;
        this.profit = profit;
    }

    public static InfoSaleMongo from(InfoSale infoSale) {
        return new InfoSaleMongo(
                infoSale.nbFarmer,
                infoSale.nbTotalHarvestableSold,
                infoSale.nbHarvestableSold,
                infoSale.isEverythingSold,
                infoSale.revenue,
                infoSale.profit
        );
    }

    public InfoSale toInfoSaleMongo() {
        return new InfoSale(nbFarmer, nbTotalHarvestableSold, nbHarvestableSold, isEverythingSold, revenue, profit);
    }
}
