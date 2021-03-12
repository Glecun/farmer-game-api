package com.glecun.farmergameapi.domain.entities;

import java.util.Objects;
import java.util.Optional;

public class HarvestableZone {
    public final HarvestableZoneType harvestableZoneType;
    private final HarvestablePlanted harvestablePlanted;
    public final boolean isLocked;

    public HarvestableZone(HarvestableZoneType harvestableZoneType, HarvestablePlanted harvestablePlanted, boolean isLocked) {
        this.harvestableZoneType = harvestableZoneType;
        this.harvestablePlanted = harvestablePlanted;
        this.isLocked = isLocked;
    }

    public Optional<HarvestablePlanted> getHarvestablePlanted() {
        return Optional.ofNullable(harvestablePlanted);
    }

    public boolean hasType(HarvestableZoneType harvestableZoneTypeToCompare) {
        return harvestableZoneType.name().equals(harvestableZoneTypeToCompare.name());
    }

    public HarvestableZone unplant(){
        return new HarvestableZone(harvestableZoneType, null, isLocked);
    }

    public HarvestableZone nullifyInfoSale() {
        HarvestablePlanted harvestablePlanted = getHarvestablePlanted()
                .map(harvestablePlanted1 -> new HarvestablePlanted(harvestablePlanted1.seedsPlanted, harvestablePlanted1.whenPlanted, null))
                .orElseThrow();
        return new HarvestableZone(harvestableZoneType, harvestablePlanted, isLocked);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HarvestableZone that = (HarvestableZone) o;
        return isLocked == that.isLocked &&
                harvestableZoneType == that.harvestableZoneType &&
                Objects.equals(harvestablePlanted, that.harvestablePlanted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(harvestableZoneType, harvestablePlanted, isLocked);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HarvestableZone{");
        sb.append("harvestableZoneType=").append(harvestableZoneType);
        sb.append(", harvestablePlanted=").append(harvestablePlanted);
        sb.append(", isLocked=").append(isLocked);
        sb.append('}');
        return sb.toString();
    }
}
