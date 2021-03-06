package com.glecun.farmergameapi.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class HarvestablePlanted {
    public final OnSaleSeed seedsPlanted;
    public final LocalDateTime whenPlanted;

    public HarvestablePlanted(OnSaleSeed seedsPlanted, LocalDateTime whenPlanted) {
        this.seedsPlanted = seedsPlanted;
        this.whenPlanted = whenPlanted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HarvestablePlanted that = (HarvestablePlanted) o;
        return Objects.equals(seedsPlanted, that.seedsPlanted) &&
                Objects.equals(whenPlanted, that.whenPlanted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seedsPlanted, whenPlanted);
    }
}
