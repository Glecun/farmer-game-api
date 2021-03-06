package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.HarvestablePlanted;

import java.time.LocalDateTime;

public class HarvestablePlantedMongo {
    public final OnSaleSeedMongo seedsPlanted;
    public final LocalDateTime whenPlanted;

    public HarvestablePlantedMongo(OnSaleSeedMongo seedsPlanted, LocalDateTime whenPlanted) {
        this.seedsPlanted = seedsPlanted;
        this.whenPlanted = whenPlanted;
    }

    public static HarvestablePlantedMongo from(HarvestablePlanted harvestablePlanted) {
        return new HarvestablePlantedMongo(OnSaleSeedMongo.from(harvestablePlanted.seedsPlanted), harvestablePlanted.whenPlanted);
    }

    public HarvestablePlanted HarvestablePlanted() {
        return new HarvestablePlanted(seedsPlanted.toOnSaleSeed(), whenPlanted);
    }
}
