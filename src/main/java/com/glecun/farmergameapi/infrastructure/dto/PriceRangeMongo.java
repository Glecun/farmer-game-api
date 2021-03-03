package com.glecun.farmergameapi.infrastructure.dto;

public class PriceRangeMongo {
    public final int min;
    public final int max;

    public PriceRangeMongo(int min, int max) {
        this.min = min;
        this.max = max;
    }
}
