package com.glecun.farmergameapi.infrastructure.dto;

import com.glecun.farmergameapi.domain.entities.HarvestableZone;
import com.glecun.farmergameapi.domain.entities.HarvestableZoneType;

public class HarvestableZoneMongo {
    public final HarvestableZoneType harvestableZoneType;
    public final OnSaleSeedMongo seedsPlanted;

    public HarvestableZoneMongo(HarvestableZoneType harvestableZoneType, OnSaleSeedMongo seedsPlanted) {
        this.harvestableZoneType = harvestableZoneType;
        this.seedsPlanted = seedsPlanted;
    }

   public static HarvestableZoneMongo from(HarvestableZone harvestableZone) {
       return new HarvestableZoneMongo(
             harvestableZone.harvestableZoneType,
             harvestableZone.getSeedsPlanted().map(OnSaleSeedMongo::from).orElse(null)
       );
   }

   public HarvestableZone toHarvestableZone() {
       return new HarvestableZone(harvestableZoneType, seedsPlanted != null ? seedsPlanted.toOnSaleSeed(): null);
   }
}
