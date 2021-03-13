package com.glecun.farmergameapi.application.dto.post;

import com.glecun.farmergameapi.domain.entities.HarvestableZoneType;
import com.glecun.farmergameapi.domain.entities.SeedEnum;

public class SeedEnumJson {

    public SeedEnum seedEnum;

    public SeedEnumJson(){}

    public SeedEnumJson(SeedEnum seedEnum) {
        this.seedEnum = seedEnum;
    }

    public SeedEnumJson(String seedEnum) {
        this.seedEnum = SeedEnum.valueOf(seedEnum);
    }
}
