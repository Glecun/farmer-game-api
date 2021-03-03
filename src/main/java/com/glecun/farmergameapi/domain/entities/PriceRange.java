package com.glecun.farmergameapi.domain.entities;

import java.util.Random;

public class PriceRange {
    public final int min;
    public final int max;

    public PriceRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int randomizeInRange() {
        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().orElse(min);
    }
}
