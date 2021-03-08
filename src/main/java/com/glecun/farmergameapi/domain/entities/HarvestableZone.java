package com.glecun.farmergameapi.domain.entities;

import java.util.Objects;
import java.util.Optional;

public class HarvestableZone {
    public final HarvestableZoneType harvestableZoneType;
    private final HarvestablePlanted harvestablePlanted;

    public HarvestableZone(HarvestableZoneType harvestableZoneType, HarvestablePlanted harvestablePlanted) {
        this.harvestableZoneType = harvestableZoneType;
        this.harvestablePlanted = harvestablePlanted;
    }

    public Optional<HarvestablePlanted> getHarvestablePlanted() {
        return Optional.ofNullable(harvestablePlanted);
    }

    public boolean hasType(HarvestableZoneType harvestableZoneTypeToCompare) {
        return harvestableZoneType.name().equals(harvestableZoneTypeToCompare.name());
    }

    public HarvestableZone unplant(){
        return new HarvestableZone(harvestableZoneType, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HarvestableZone that = (HarvestableZone) o;
        return harvestableZoneType == that.harvestableZoneType &&
                Objects.equals(harvestablePlanted, that.harvestablePlanted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(harvestableZoneType, harvestablePlanted);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HarvestableZone{");
        sb.append("harvestableZoneType=").append(harvestableZoneType);
        sb.append(", harvestablePlanted=").append(harvestablePlanted);
        sb.append('}');
        return sb.toString();
    }
}
