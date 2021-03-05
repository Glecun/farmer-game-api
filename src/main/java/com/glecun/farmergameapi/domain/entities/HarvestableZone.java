package com.glecun.farmergameapi.domain.entities;

import java.util.Objects;
import java.util.Optional;

public class HarvestableZone {
    public final HarvestableZoneType harvestableZoneType;
    private final OnSaleSeed seedsPlanted;

    public HarvestableZone(HarvestableZoneType harvestableZoneType, OnSaleSeed seedsPlanted) {
        this.harvestableZoneType = harvestableZoneType;
        this.seedsPlanted = seedsPlanted;
    }

    public Optional<OnSaleSeed> getSeedsPlanted() {
        return Optional.ofNullable(seedsPlanted);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HarvestableZone that = (HarvestableZone) o;
        return harvestableZoneType == that.harvestableZoneType && Objects.equals(getSeedsPlanted(), that.getSeedsPlanted());
    }

    @Override
    public int hashCode() {
        return Objects.hash(harvestableZoneType, getSeedsPlanted());
    }
}
