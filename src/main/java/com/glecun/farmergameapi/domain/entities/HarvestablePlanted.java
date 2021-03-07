package com.glecun.farmergameapi.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class HarvestablePlanted {
    public final OnSaleSeed seedsPlanted;
    public final LocalDateTime whenPlanted;
    private final InfoSale infoSale;

    public HarvestablePlanted(OnSaleSeed seedsPlanted, LocalDateTime whenPlanted, InfoSale infoSale) {
        this.seedsPlanted = seedsPlanted;
        this.whenPlanted = whenPlanted;
        this.infoSale = infoSale;
    }

    public Optional<InfoSale> getInfoSale() {
        return Optional.ofNullable(infoSale);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HarvestablePlanted that = (HarvestablePlanted) o;
        return Objects.equals(seedsPlanted, that.seedsPlanted) &&
                Objects.equals(whenPlanted, that.whenPlanted) &&
                Objects.equals(infoSale, that.infoSale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seedsPlanted, whenPlanted, infoSale);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HarvestablePlanted{");
        sb.append("seedsPlanted=").append(seedsPlanted);
        sb.append(", whenPlanted=").append(whenPlanted);
        sb.append(", infoSale=").append(infoSale);
        sb.append('}');
        return sb.toString();
    }
}
