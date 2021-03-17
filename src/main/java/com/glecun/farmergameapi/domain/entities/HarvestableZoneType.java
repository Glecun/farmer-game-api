package com.glecun.farmergameapi.domain.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum HarvestableZoneType {
    ZONE_1_TIER_1(false, 12, 0),
    ZONE_2_TIER_1(true, 12, 240),
    ZONE_3_TIER_1(true, 12, 480),
    ZONE_4_TIER_1(true, 12, 1920),
    ZONE_5_TIER_1(true, 40, 3520),

    ZONE_1_TIER_2(false, 12, 0),
    ZONE_2_TIER_2(true, 12, 2600),
    ZONE_3_TIER_2(true, 12, 5280),
    ZONE_4_TIER_2(true, 12, 21120),
    ZONE_5_TIER_2(true, 40, 38720),

    ZONE_1_TIER_3(false, 12, 0),
    ZONE_2_TIER_3(true, 12, 17000),
    ZONE_3_TIER_3(true, 12, 34080),
    ZONE_4_TIER_3(true, 12, 136320),
    ZONE_5_TIER_3(true, 40, 249920),

    ZONE_1_TIER_4(false, 12, 0),
    ZONE_2_TIER_4(true, 12, 45000),
    ZONE_3_TIER_4(true, 12, 91680),
    ZONE_4_TIER_4(true, 12, 366720),
    ZONE_5_TIER_4(true, 40, 672320),

    ZONE_1_TIER_5(false, 12, 0),
    ZONE_2_TIER_5(true, 12, 130000),
    ZONE_3_TIER_5(true, 12, 264480),
    ZONE_4_TIER_5(true, 12, 1057920),
    ZONE_5_TIER_5(true, 40, 1939520),

    ZONE_1_TIER_6(false, 12, 0),
    ZONE_2_TIER_6(true, 12, 470000),
    ZONE_3_TIER_6(true, 12, 955680),
    ZONE_4_TIER_6(true, 12, 3822720),
    ZONE_5_TIER_6(true, 40, 7008320),


    ZONE_1_TIER_7(false, 12, 0),
    ZONE_2_TIER_7(true, 12, 1100000),
    ZONE_3_TIER_7(true, 12, 2338080),
    ZONE_4_TIER_7(true, 12, 9352320),
    ZONE_5_TIER_7(true, 40, 17145920);

    public final boolean lockedByDefault;
    public final int nbOfZone;
    public final int price;


    HarvestableZoneType(boolean lockedByDefault, int nbOfZone, int price) {
        this.lockedByDefault = lockedByDefault;
        this.nbOfZone = nbOfZone;
        this.price = price;
    }
}
