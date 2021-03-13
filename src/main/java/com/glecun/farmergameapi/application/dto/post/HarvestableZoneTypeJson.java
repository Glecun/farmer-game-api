package com.glecun.farmergameapi.application.dto.post;

import com.glecun.farmergameapi.domain.entities.HarvestableZoneType;

public class HarvestableZoneTypeJson {

    public HarvestableZoneType harvestableZoneType;

    public HarvestableZoneTypeJson(){}

    public HarvestableZoneTypeJson(HarvestableZoneType harvestableZoneType) {
        this.harvestableZoneType = harvestableZoneType;
    }

    public HarvestableZoneTypeJson(String harvestableZoneType) {
        this.harvestableZoneType = HarvestableZoneType.valueOf(harvestableZoneType);
    }
}
