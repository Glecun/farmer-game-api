package com.glecun.farmergameapi.application.dto;

import com.glecun.farmergameapi.domain.entities.HarvestablePlanted;

import java.time.LocalDateTime;

public class HarvestablePlantedJson {
    public final OnSaleSeedJson seedsPlanted;
    public final LocalDateTime whenPlanted;
    private final InfoSaleJson infoSale;

    public HarvestablePlantedJson(OnSaleSeedJson seedsPlanted, LocalDateTime whenPlanted, InfoSaleJson infoSale) {
        this.seedsPlanted = seedsPlanted;
        this.whenPlanted = whenPlanted;
        this.infoSale = infoSale;
    }

    public static HarvestablePlantedJson from(HarvestablePlanted harvestablePlanted) {
        return new HarvestablePlantedJson(
                OnSaleSeedJson.from(harvestablePlanted.seedsPlanted),
                harvestablePlanted.whenPlanted,
                harvestablePlanted.getInfoSale().map(InfoSaleJson::from).orElse(null)
        );
    }

    public HarvestablePlanted toHarvestableZone() {
        return new HarvestablePlanted(
                seedsPlanted.toOnSaleSeed(),
                whenPlanted,
                null
                );
    }
}
