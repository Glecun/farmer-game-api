package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.HarvestableZone;
import com.glecun.farmergameapi.domain.entities.HarvestableZoneType;

public class HarvestableZoneMongo {
    public final HarvestableZoneType harvestableZoneType;
    public final HarvestablePlantedMongo harvestablePlanted;

    public HarvestableZoneMongo(HarvestableZoneType harvestableZoneType, HarvestablePlantedMongo harvestablePlanted) {
        this.harvestableZoneType = harvestableZoneType;
        this.harvestablePlanted = harvestablePlanted;
    }

   public static HarvestableZoneMongo from(HarvestableZone harvestableZone) {
       return new HarvestableZoneMongo(
             harvestableZone.harvestableZoneType,
             harvestableZone.getHarvestablePlanted().map(HarvestablePlantedMongo::from).orElse(null)
       );
   }

   public HarvestableZone toHarvestableZone() {
       return new HarvestableZone(harvestableZoneType, harvestablePlanted != null ? harvestablePlanted.HarvestablePlanted(): null);
   }
}
