package com.glecun.farmergameapi.application.dto;

import com.glecun.farmergameapi.domain.entities.HarvestablePlanted;
import com.glecun.farmergameapi.domain.entities.OnSaleSeed;

import java.time.LocalDateTime;

public class HarvestablePlantedJson {
    public final OnSaleSeedJson seedsPlanted;
    public final LocalDateTime whenPlanted;

    public HarvestablePlantedJson(OnSaleSeedJson seedsPlanted, LocalDateTime whenPlanted) {
        this.seedsPlanted = seedsPlanted;
        this.whenPlanted = whenPlanted;
    }

    public static HarvestablePlantedJson from(HarvestablePlanted harvestablePlanted) {
        return new HarvestablePlantedJson(OnSaleSeedJson.from(harvestablePlanted.seedsPlanted), harvestablePlanted.whenPlanted);
    }

    public HarvestablePlanted toHarvestableZone() {
        return new HarvestablePlanted(seedsPlanted.toOnSaleSeed(), whenPlanted);
    }
}
