package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.HarvestablePlanted;
import com.glecun.farmergameapi.domain.entities.InfoSale;

import java.time.LocalDateTime;

public class HarvestablePlantedMongo {
    public final OnSaleSeedMongo seedsPlanted;
    public final LocalDateTime whenPlanted;
    public final InfoSaleMongo infoSale;

    public HarvestablePlantedMongo(OnSaleSeedMongo seedsPlanted, LocalDateTime whenPlanted, InfoSaleMongo infoSale) {
        this.seedsPlanted = seedsPlanted;
        this.whenPlanted = whenPlanted;
        this.infoSale = infoSale;
    }

    public static HarvestablePlantedMongo from(HarvestablePlanted harvestablePlanted) {
        return new HarvestablePlantedMongo(
                OnSaleSeedMongo.from(harvestablePlanted.seedsPlanted),
                harvestablePlanted.whenPlanted,
                harvestablePlanted.getInfoSale().map(InfoSaleMongo::from).orElse(null)
        );
    }

    public HarvestablePlanted HarvestablePlanted() {
        return new HarvestablePlanted(seedsPlanted.toOnSaleSeed(), whenPlanted, infoSale!=null ? infoSale.toInfoSaleMongo() : null);
    }
}
