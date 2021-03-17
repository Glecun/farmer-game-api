package com.glecun.farmergameapi.domain.entities;

import java.util.Arrays;
import java.util.List;

import static com.glecun.farmergameapi.domain.entities.HarvestableZoneType.*;
import static com.glecun.farmergameapi.domain.entities.SeedEnum.*;

public enum TierEnum {

    TIER_1(List.of(ZONE_1_TIER_1, ZONE_2_TIER_1, ZONE_3_TIER_1, ZONE_4_TIER_1, ZONE_5_TIER_1), List.of(GREEN_BEAN, BEETS, CARROT), 0),
    TIER_2(List.of(ZONE_1_TIER_2, ZONE_2_TIER_2, ZONE_3_TIER_2, ZONE_4_TIER_2, ZONE_5_TIER_2), List.of(PEA, POTATO, GRAPE, BLUEBERRY), 2500),
    TIER_3(List.of(ZONE_1_TIER_3, ZONE_2_TIER_3, ZONE_3_TIER_3, ZONE_4_TIER_3, ZONE_5_TIER_3), List.of(PEPPER_GREEN, PEPPER_YELLOW, PEPPER_RED), 30000),
    TIER_4(List.of(ZONE_1_TIER_4, ZONE_2_TIER_4, ZONE_3_TIER_4, ZONE_4_TIER_4, ZONE_5_TIER_4), List.of(STRAWBERRY, ONION, CUCUMBER), 190000),
    TIER_5(List.of(ZONE_1_TIER_5, ZONE_2_TIER_5, ZONE_3_TIER_5, ZONE_4_TIER_5, ZONE_5_TIER_5), List.of(TOMATO, MELON, CORN), 500000),
    TIER_6(List.of(ZONE_1_TIER_6, ZONE_2_TIER_6, ZONE_3_TIER_6, ZONE_4_TIER_6, ZONE_5_TIER_6), List.of(CABBAGE, GARLIC, CAULIFLOWER), 1400000),
    TIER_7(List.of(ZONE_1_TIER_7, ZONE_2_TIER_7, ZONE_3_TIER_7, ZONE_4_TIER_7, ZONE_5_TIER_7), List.of(WHEAT, SUGAR_CANE, ASPARAGUS), 5000000);

    public final List<HarvestableZoneType> zones;
    public final List<SeedEnum> seeds;
    public final int priceToUnlock;

    TierEnum(List<HarvestableZoneType> zones, List<SeedEnum> seeds, int priceToUnlock) {
        this.zones = zones;
        this.seeds = seeds;
        this.priceToUnlock = priceToUnlock;
    }

    public static boolean isSeedAndZoneInSameTier(HarvestableZoneType zone, SeedEnum seed) {
        return Arrays.stream(TierEnum.values()).anyMatch(tierEnum -> tierEnum.zones.contains(zone) && tierEnum.seeds.contains(seed));
    }

    public static TierEnum getTierOfZone(HarvestableZoneType zone) {
        return Arrays.stream(TierEnum.values()).filter(tierEnum -> tierEnum.zones.contains(zone)).findFirst().orElseThrow();
    }
}
