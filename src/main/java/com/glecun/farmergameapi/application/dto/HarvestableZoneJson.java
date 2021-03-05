package com.glecun.farmergameapi.application.dto;

import com.glecun.farmergameapi.domain.entities.HarvestableZone;
import com.glecun.farmergameapi.domain.entities.HarvestableZoneType;

public class HarvestableZoneJson {
    public final HarvestableZoneType harvestableZoneType;
    public final OnSaleSeedJson seedsPlanted;

    public HarvestableZoneJson(HarvestableZoneType harvestableZoneType, OnSaleSeedJson seedsPlanted) {
        this.harvestableZoneType = harvestableZoneType;
        this.seedsPlanted = seedsPlanted;
    }

    public static HarvestableZoneJson from(HarvestableZone harvestableZone) {
        return new HarvestableZoneJson(
              harvestableZone.harvestableZoneType,
              harvestableZone.getSeedsPlanted().map(OnSaleSeedJson::from).orElse(null)
        );
    }
}
