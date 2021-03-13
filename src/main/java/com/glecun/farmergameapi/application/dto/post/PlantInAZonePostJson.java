package com.glecun.farmergameapi.application.dto.post;

import com.glecun.farmergameapi.domain.entities.HarvestableZoneType;

public class PlantInAZonePostJson {

    public HarvestableZoneTypeJson harvestableZoneTypeJson;
    public SeedEnumJson seedEnumJson;

    public PlantInAZonePostJson() { }

    public PlantInAZonePostJson(HarvestableZoneTypeJson harvestableZoneTypeJson, SeedEnumJson seedEnumJson) {
        this.harvestableZoneTypeJson = harvestableZoneTypeJson;
        this.seedEnumJson = seedEnumJson;
    }
}
