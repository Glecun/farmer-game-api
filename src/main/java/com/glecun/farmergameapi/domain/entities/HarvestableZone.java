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

    public boolean hasType(HarvestableZoneType harvestableZoneTypeToCompare) {
        return harvestableZoneType.name().equals(harvestableZoneTypeToCompare.name());
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HarvestableZone{");
        sb.append("harvestableZoneType=").append(harvestableZoneType);
        sb.append(", seedsPlanted=").append(seedsPlanted);
        sb.append('}');
        return sb.toString();
    }
}
