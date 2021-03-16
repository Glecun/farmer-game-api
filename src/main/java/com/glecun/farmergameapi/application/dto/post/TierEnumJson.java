package com.glecun.farmergameapi.application.dto.post;

import com.glecun.farmergameapi.domain.entities.SeedEnum;
import com.glecun.farmergameapi.domain.entities.TierEnum;

public class TierEnumJson {
    public TierEnum tierEnum;

    public TierEnumJson(){}

    public TierEnumJson(TierEnum tierEnum) {
        this.tierEnum = tierEnum;
    }

    public TierEnumJson(String tierEnum) {
        this.tierEnum = TierEnum.valueOf(tierEnum);
    }
}
