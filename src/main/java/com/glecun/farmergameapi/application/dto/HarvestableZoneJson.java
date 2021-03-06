package com.glecun.farmergameapi.application.dto;

import com.glecun.farmergameapi.domain.entities.HarvestableZone;
import com.glecun.farmergameapi.domain.entities.HarvestableZoneType;

public class HarvestableZoneJson {
    public final HarvestableZoneType harvestableZoneType;
    public final HarvestablePlantedJson harvestablePlanted;

    public HarvestableZoneJson(HarvestableZoneType harvestableZoneType, HarvestablePlantedJson harvestablePlanted) {
        this.harvestableZoneType = harvestableZoneType;
        this.harvestablePlanted = harvestablePlanted;
    }

    public static HarvestableZoneJson from(HarvestableZone harvestableZone) {
        return new HarvestableZoneJson(
              harvestableZone.harvestableZoneType,
              harvestableZone.getHarvestablePlanted().map(HarvestablePlantedJson::from).orElse(null)
        );
    }

    public HarvestableZone toHarvestableZone() {
        return new HarvestableZone(harvestableZoneType, harvestablePlanted.toHarvestableZone());
    }
}
