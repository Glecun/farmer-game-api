package com.glecun.farmergameapi.domain;

import com.glecun.farmergameapi.domain.entities.*;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.glecun.farmergameapi.domain.entities.HarvestableZoneType.*;
import static java.util.Collections.*;
import static java.util.List.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResolveSalesTest {

    @Mock
    private UserInfoPort userInfoPort;

    @InjectMocks
    ResolveSales resolveSales;

    @Captor
    ArgumentCaptor<List<UserInfo>> userInfoCaptor;

    @BeforeEach
    void beforeEach(){
        ReflectionTestUtils.setField(resolveSales, "fakeUserUsed", false);
    }

    @Test
    void should_resolve_sales_when_everyone_satisfied() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        OnSaleSeed onSaleSeed = OnSaleSeed.builder().willBeSoldDate(now).buyPrice(2).sellPrice(3).seedEnum(SeedEnum.BEETS).demand(new Demand(DemandType.VeryHighDemand, 0)).build();
        HarvestablePlanted harvestablePlanted1 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo1 = new UserInfo("1", "greg.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_2_TIER_1, harvestablePlanted1, false), new HarvestableZone(ZONE_5_TIER_1, null, false)), singletonList(TierEnum.TIER_1), now);
        HarvestablePlanted harvestablePlanted2 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo2 = new UserInfo("2", "greg2.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_1_TIER_1, harvestablePlanted2, false), new HarvestableZone(ZONE_5_TIER_1, null, false)), singletonList(TierEnum.TIER_1), now);
        HarvestablePlanted harvestablePlanted3 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo3 = new UserInfo("3", "greg3.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_2_TIER_1, harvestablePlanted3, false), new HarvestableZone(ZONE_5_TIER_1, null, false)), singletonList(TierEnum.TIER_1), now);
        when(userInfoPort.findAll()).thenReturn(of(userInfo1, userInfo2, userInfo3));
        when(userInfoPort.countAll()).thenReturn(5L);

        resolveSales.execute(GrowthTime.GROWTH_TIME_1);

        verify(userInfoPort).saveAll(userInfoCaptor.capture());
        List<UserInfo> value = userInfoCaptor.getValue();
        assertThat(value).containsExactlyInAnyOrder(
                new UserInfo("1", "greg.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_2_TIER_1, new HarvestablePlanted(onSaleSeed.SetNbDemand(109), now,
                        new InfoSale(3, 15, 36, 12, true, 36, 12)), false), new HarvestableZone(ZONE_5_TIER_1, null, false)), singletonList(TierEnum.TIER_1), now),
                new UserInfo("2", "greg2.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_1_TIER_1, new HarvestablePlanted(onSaleSeed.SetNbDemand(109), now,
                        new InfoSale(3, 15, 36, 12, true, 36, 12)), false), new HarvestableZone(ZONE_5_TIER_1, null, false)), singletonList(TierEnum.TIER_1), now),
                new UserInfo("3", "greg3.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_2_TIER_1, new HarvestablePlanted(onSaleSeed.SetNbDemand(109), now,
                        new InfoSale(3, 15, 36, 12, true, 36, 12)), false), new HarvestableZone(ZONE_5_TIER_1, null, false)), singletonList(TierEnum.TIER_1), now)
        );
    }

    @Test
    void should_reduce_sales_when_not_everyone_satisfied() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        OnSaleSeed onSaleSeed = OnSaleSeed.builder().willBeSoldDate(now).buyPrice(2).sellPrice(3).seedEnum(SeedEnum.BEETS).demand(new Demand(DemandType.SmallDemand, 0)).build();
        HarvestablePlanted harvestablePlanted1 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo1 = new UserInfo("1", "greg.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_2_TIER_1, harvestablePlanted1, false)), of(TierEnum.TIER_1), now);
        HarvestablePlanted harvestablePlanted2 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo2 = new UserInfo("2", "greg2.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_1_TIER_1, harvestablePlanted2, false)), of(TierEnum.TIER_1), now);
        HarvestablePlanted harvestablePlanted3 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo3 = new UserInfo("3", "greg3.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_2_TIER_1, harvestablePlanted3, false)), of(TierEnum.TIER_1), now);
        when(userInfoPort.findAll()).thenReturn(of(userInfo1, userInfo2, userInfo3));

        resolveSales.execute(GrowthTime.GROWTH_TIME_1);

        verify(userInfoPort).saveAll(userInfoCaptor.capture());
        List<UserInfo> userInfos = userInfoCaptor.getValue();
        Integer nbTotalHarvestableSold = userInfos.stream()
                .map(userInfo -> userInfo.harvestableZones).flatMap(Collection::stream)
                .map(HarvestableZone::getHarvestablePlanted).flatMap(Optional::stream)
                .map(HarvestablePlanted::getInfoSale).flatMap(Optional::stream)
                .map(infoSale -> infoSale.nbHarvestableSold)
                .reduce(0, Integer::sum);

        var expectedDemand = Math.round((ZONE_1_TIER_1.nbOfZone + (ZONE_2_TIER_1.nbOfZone * 2)) * ((float)DemandType.SmallDemand.percentOfNbZones/100));
        assertThat(nbTotalHarvestableSold).isEqualTo(expectedDemand);
    }

    @Test
    void should_not_erase_InfoSale_when_already_set() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        var onSaleSeedBuilder = OnSaleSeed.builder().willBeSoldDate(now).buyPrice(2).sellPrice(3).seedEnum(SeedEnum.BEETS).demand(new Demand(DemandType.VeryHighDemand, 0));
        OnSaleSeed onSaleSeed = onSaleSeedBuilder.build();
        OnSaleSeed onSaleSeedBefore = onSaleSeedBuilder.willBeSoldDate(now.minusDays(1)).build();
        HarvestablePlanted harvestablePlanted1 = new HarvestablePlanted(onSaleSeedBefore, now, new InfoSale(10, 2, 10,10,true,10,10));
        var userInfo1 = new UserInfo("1", "greg.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_2_TIER_1, harvestablePlanted1, false)), singletonList(TierEnum.TIER_1), now);
        HarvestablePlanted harvestablePlanted2Zone1 = new HarvestablePlanted(onSaleSeedBefore, now, new InfoSale(10, 2, 10,10,true,10,10));
        HarvestablePlanted harvestablePlanted2Zone2 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo2 = new UserInfo("2", "greg2.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_1_TIER_1, harvestablePlanted2Zone1, false), new HarvestableZone(ZONE_2_TIER_1, harvestablePlanted2Zone2, false)), singletonList(TierEnum.TIER_1), now);
        when(userInfoPort.findAll()).thenReturn(of(userInfo1, userInfo2));
        when(userInfoPort.countAll()).thenReturn(1L);

        resolveSales.execute(GrowthTime.GROWTH_TIME_1);

        verify(userInfoPort).saveAll(userInfoCaptor.capture());
        List<UserInfo> value = userInfoCaptor.getValue();
        assertThat(value).containsExactlyInAnyOrder(
                new UserInfo("2", "greg2.lol@mdr.fr", 200, null, of(
                        new HarvestableZone(ZONE_1_TIER_1, new HarvestablePlanted(onSaleSeedBefore, now, new InfoSale(10, 2, 10,10,true,10,10)), false),
                        new HarvestableZone(ZONE_2_TIER_1, new HarvestablePlanted(onSaleSeed.SetNbDemand(25), now, new InfoSale(1, 11, 12, 12, true, 36, 12)), false)
                ), singletonList(TierEnum.TIER_1), now)
        );
    }

    @Test
    void should_not_resolve_sales_when_onSaleSeed_is_not_old_enough() {
        //GIVEN
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        OnSaleSeed onSaleSeed = OnSaleSeed.builder().willBeSoldDate(now.plusMinutes(2)).buyPrice(2).sellPrice(3).seedEnum(SeedEnum.BEETS).demand(new Demand(DemandType.VeryHighDemand, 0)).build();
        HarvestablePlanted harvestablePlanted1 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo1 = new UserInfo("1", "greg.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_2_TIER_1, harvestablePlanted1, false)),singletonList(TierEnum.TIER_1), now);

        HarvestablePlanted harvestablePlanted2Zone1 = new HarvestablePlanted(onSaleSeed, now, null);
        OnSaleSeed onSaleSeed2 = OnSaleSeed.builder().willBeSoldDate(now).buyPrice(2).sellPrice(3).seedEnum(SeedEnum.BEETS).demand(new Demand(DemandType.VeryHighDemand, 0)).build();
        HarvestablePlanted harvestablePlanted2Zone2 = new HarvestablePlanted(onSaleSeed2, now, null);
        var userInfo2 = new UserInfo("2", "greg2.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_1_TIER_1, harvestablePlanted2Zone1, false), new HarvestableZone(ZONE_2_TIER_1, harvestablePlanted2Zone2, false), new HarvestableZone(ZONE_4_TIER_1, null, false), new HarvestableZone(ZONE_5_TIER_1, null, false)), singletonList(TierEnum.TIER_1), now);

        when(userInfoPort.findAll()).thenReturn(of(userInfo1, userInfo2));
        when(userInfoPort.countAll()).thenReturn(1L);

        //WHEN
        resolveSales.execute(GrowthTime.GROWTH_TIME_1);

        //THEN
        verify(userInfoPort).saveAll(userInfoCaptor.capture());
        List<UserInfo> value = userInfoCaptor.getValue();
        assertThat(value.get(0)).isEqualTo(
                new UserInfo("2", "greg2.lol@mdr.fr", 200, null, of(
                        new HarvestableZone(ZONE_1_TIER_1, new HarvestablePlanted(onSaleSeed, now, null), false),
                        new HarvestableZone(ZONE_2_TIER_1, new HarvestablePlanted(onSaleSeed2.SetNbDemand(62), now, new InfoSale(1, 11, 12, 12, true, 36, 12)), false),
                        new HarvestableZone(ZONE_4_TIER_1, null, false),
                        new HarvestableZone(ZONE_5_TIER_1, null, false)
                ), singletonList(TierEnum.TIER_1), now)
        );
    }

    @Test
    void should_not_infinite_loop_if_more_demand_than_production() {
        ReflectionTestUtils.setField(resolveSales, "fakeUserUsed", true);
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        OnSaleSeed onSaleSeed = OnSaleSeed.builder().willBeSoldDate(now).buyPrice(2).sellPrice(3).seedEnum(SeedEnum.BEETS).demand(new Demand(DemandType.VerySmallDemand, 1)).build();
        HarvestablePlanted harvestablePlanted1 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo1 = new UserInfo("1", "greg.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_5_TIER_1, harvestablePlanted1, false)), emptyList(), now);
        when(userInfoPort.findAll()).thenReturn(of(userInfo1));
        when(userInfoPort.countAll()).thenReturn(1L);

        resolveSales.execute(GrowthTime.GROWTH_TIME_1);

        verify(userInfoPort).saveAll(anyList());
    }

    @Test
    void should_not_takes_zone_if_no_fake_users() {
        ReflectionTestUtils.setField(resolveSales, "fakeUserUsed", true);
        Function<Demand, Integer> randomizeNbFakePlayers = (Demand demand) -> 0;
        ReflectionTestUtils.setField(resolveSales, "randomizeNbFakePlayers", randomizeNbFakePlayers);
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        OnSaleSeed onSaleSeed = OnSaleSeed.builder().willBeSoldDate(now).buyPrice(2).sellPrice(3).seedEnum(SeedEnum.BEETS).demand(new Demand(DemandType.VeryHighDemand, 5000)).build();
        HarvestablePlanted harvestablePlanted1 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo1 = new UserInfo("1", "greg.lol@mdr.fr", 200, null, of(new HarvestableZone(ZONE_2_TIER_1, harvestablePlanted1, false)), emptyList(), now);
        when(userInfoPort.findAll()).thenReturn(of(userInfo1));
        when(userInfoPort.countAll()).thenReturn(1L);

        resolveSales.execute(GrowthTime.GROWTH_TIME_1);

        verify(userInfoPort).saveAll(userInfoCaptor.capture());
        List<UserInfo> value = userInfoCaptor.getValue();
        InfoSale infoSale = value.get(0).harvestableZones.get(0).getHarvestablePlanted()
                .flatMap(HarvestablePlanted::getInfoSale).orElseThrow();
        assertThat(infoSale.nbHarvestableSold).isEqualTo(12);
        assertThat(infoSale.nbTotalHarvestableSold).isEqualTo(12);
        assertThat(infoSale.nbFarmer).isEqualTo(1);
        assertThat(infoSale.nbTotalFarmer).isEqualTo(11);
    }

    @Test
    void should_calculate_nbDemand() {
        ReflectionTestUtils.setField(resolveSales, "fakeUserUsed", true);
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        var seedEnum = SeedEnum.BEETS;
        OnSaleSeed onSaleSeed = OnSaleSeed.builder().willBeSoldDate(now).buyPrice(2).sellPrice(3).seedEnum(seedEnum).demand(new Demand(DemandType.HighDemand, 0)).build();
        HarvestablePlanted harvestablePlanted = new HarvestablePlanted(onSaleSeed, now, null);

        var z1T1NotLocked = new HarvestableZone(ZONE_1_TIER_1, harvestablePlanted, false);
        var z2T1NotLocked = new HarvestableZone(ZONE_2_TIER_1, harvestablePlanted, false);
        var z5T1NotLocked = new HarvestableZone(ZONE_5_TIER_1, harvestablePlanted, false);
        var z3T1Locked = new HarvestableZone(ZONE_3_TIER_1, harvestablePlanted, true);
        var z2T2Locked = new HarvestableZone(ZONE_2_TIER_2, harvestablePlanted, false);
        var userInfo1 = new UserInfo("1", "greg.lol@mdr.fr", 200, null,  of(z1T1NotLocked, z2T1NotLocked, z3T1Locked), of(TierEnum.TIER_1), now);
        var userInfo2 = new UserInfo("2", "greg2.lol@mdr.fr", 200, null, of(z5T1NotLocked), of(TierEnum.TIER_1), now);
        var userInfo3 = new UserInfo("3", "greg3.lol@mdr.fr", 200, null, of(z1T1NotLocked, z2T2Locked), emptyList(), now);
        var userInfo4 = new UserInfo("3", "greg3.lol@mdr.fr", 200, null, of(z1T1NotLocked), of(TierEnum.TIER_1), now.minusHours(1));
        when(userInfoPort.findAll()).thenReturn(of(userInfo1, userInfo2, userInfo3, userInfo4));

        var nbDemand = resolveSales.calculateNbDemand(DemandType.HighDemand, seedEnum);

        Integer usersZoneCapacity = z1T1NotLocked.harvestableZoneType.nbOfZone + z2T1NotLocked.harvestableZoneType.nbOfZone + z5T1NotLocked.harvestableZoneType.nbOfZone;
        Integer fakePlayersZoneCapacity = (12 + 12 + 12 + 12 + 40) * 10;
        int expectedDemand = Math.round((usersZoneCapacity+fakePlayersZoneCapacity) * ((float)DemandType.HighDemand.percentOfNbZones/100));

        assertThat(nbDemand).isEqualTo(expectedDemand);
    }

}
