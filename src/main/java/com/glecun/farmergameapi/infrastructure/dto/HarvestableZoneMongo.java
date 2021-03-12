package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.HarvestableZone;
import com.glecun.farmergameapi.domain.entities.HarvestableZoneType;

public class HarvestableZoneMongo {
    public final HarvestableZoneType harvestableZoneType;
    public final HarvestablePlantedMongo harvestablePlanted;
    public final boolean isLocked;

    public HarvestableZoneMongo(HarvestableZoneType harvestableZoneType, HarvestablePlantedMongo harvestablePlanted, boolean isLocked) {
        this.harvestableZoneType = harvestableZoneType;
        this.harvestablePlanted = harvestablePlanted;
       this.isLocked = isLocked;
    }

   public static HarvestableZoneMongo from(HarvestableZone harvestableZone) {
       return new HarvestableZoneMongo(
             harvestableZone.harvestableZoneType,
             harvestableZone.getHarvestablePlanted().map(HarvestablePlantedMongo::from).orElse(null),
             harvestableZone.isLocked
       );
   }

   public HarvestableZone toHarvestableZone() {
       return new HarvestableZone(harvestableZoneType, harvestablePlanted != null ? harvestablePlanted.HarvestablePlanted(): null, isLocked);
   }
}
