package com.glecun.farmergameapi.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

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

    public InfoSale getInfoSale() {
        return getHarvestablePlanted().map(HarvestablePlanted::getInfoSale)
                .flatMap(Function.identity()).orElseThrow();
    }

    public HarvestableZone plant(OnSaleSeed onSaleSeed, Supplier<LocalDateTime> now) {
        HarvestablePlanted harvestablePlanted = new HarvestablePlanted(onSaleSeed, now.get(), null);
        return new HarvestableZone(harvestableZoneType, harvestablePlanted, isLocked);
    }

    public HarvestableZone SetOnSaleSeed(OnSaleSeed onSaleSeed) {
        HarvestablePlanted newHarvestablePlanted = getHarvestablePlanted()
                .map(harvestablePlanted -> harvestablePlanted.setSeedsPlanted(onSaleSeed))
                .orElseThrow();
        return new HarvestableZone(harvestableZoneType, newHarvestablePlanted, isLocked);
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
