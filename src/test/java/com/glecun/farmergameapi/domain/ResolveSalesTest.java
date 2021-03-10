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

import static org.assertj.core.api.Assertions.assertThat;
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
        OnSaleSeed onSaleSeed = OnSaleSeed.builder().willBeSoldDate(now).buyPrice(2).sellPrice(3).seedEnum(SeedEnum.BEETS).demand(new Demand(DemandType.VeryHighDemand, 5000)).build();
        HarvestablePlanted harvestablePlanted1 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo1 = new UserInfo("1", "greg.lol@mdr.fr", 200, 0, List.of(new HarvestableZone(HarvestableZoneType.ZONE_2, harvestablePlanted1)) );
        HarvestablePlanted harvestablePlanted2 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo2 = new UserInfo("2", "greg2.lol@mdr.fr", 200, 0, List.of(new HarvestableZone(HarvestableZoneType.ZONE_1, harvestablePlanted2)) );
        HarvestablePlanted harvestablePlanted3 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo3 = new UserInfo("3", "greg3.lol@mdr.fr", 200, 0, List.of(new HarvestableZone(HarvestableZoneType.ZONE_2, harvestablePlanted3)) );
        when(userInfoPort.findAll()).thenReturn(List.of(userInfo1, userInfo2, userInfo3));
        when(userInfoPort.countAll()).thenReturn(5L);

        resolveSales.execute(GrowthTime.SECOND_GROWTH_TIME);

        verify(userInfoPort).saveAll(userInfoCaptor.capture());
        List<UserInfo> value = userInfoCaptor.getValue();
        assertThat(value).containsExactlyInAnyOrder(
                new UserInfo("1", "greg.lol@mdr.fr", 200, 0, List.of(new HarvestableZone(HarvestableZoneType.ZONE_2, new HarvestablePlanted(onSaleSeed, now,
                        new InfoSale(3, 15, 36, 12, true, 36, 12)
                )))),
                new UserInfo("2", "greg2.lol@mdr.fr", 200, 0, List.of(new HarvestableZone(HarvestableZoneType.ZONE_1, new HarvestablePlanted(onSaleSeed, now,
                        new InfoSale(3, 15, 36, 12, true, 36, 12)
                )))),
                new UserInfo("3", "greg3.lol@mdr.fr", 200, 0, List.of(new HarvestableZone(HarvestableZoneType.ZONE_2, new HarvestablePlanted(onSaleSeed, now,
                        new InfoSale(3, 15, 36, 12, true, 36, 12)
                ))))
        );
    }

    @Test
    void should_reduce_sales_when_everyone_satisfied() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        OnSaleSeed onSaleSeed = OnSaleSeed.builder().willBeSoldDate(now).buyPrice(2).sellPrice(3).seedEnum(SeedEnum.BEETS).demand(new Demand(DemandType.VerySmallDemand, 10)).build();
        HarvestablePlanted harvestablePlanted1 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo1 = new UserInfo("1", "greg.lol@mdr.fr", 200, 0, List.of(new HarvestableZone(HarvestableZoneType.ZONE_2, harvestablePlanted1)) );
        HarvestablePlanted harvestablePlanted2 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo2 = new UserInfo("2", "greg2.lol@mdr.fr", 200, 0, List.of(new HarvestableZone(HarvestableZoneType.ZONE_1, harvestablePlanted2)) );
        HarvestablePlanted harvestablePlanted3 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo3 = new UserInfo("3", "greg3.lol@mdr.fr", 200, 0, List.of(new HarvestableZone(HarvestableZoneType.ZONE_2, harvestablePlanted3)) );
        when(userInfoPort.findAll()).thenReturn(List.of(userInfo1, userInfo2, userInfo3));

        resolveSales.execute(GrowthTime.SECOND_GROWTH_TIME);

        verify(userInfoPort).saveAll(userInfoCaptor.capture());
        List<UserInfo> userInfos = userInfoCaptor.getValue();
        Integer nbTotalHarvestableSold = userInfos.stream()
                .map(userInfo -> userInfo.harvestableZones).flatMap(Collection::stream)
                .map(HarvestableZone::getHarvestablePlanted).flatMap(Optional::stream)
                .map(HarvestablePlanted::getInfoSale).flatMap(Optional::stream)
                .map(infoSale -> infoSale.nbHarvestableSold)
                .reduce(0, Integer::sum);

        assertThat(nbTotalHarvestableSold).isEqualTo(10);
    }

    @Test
    void should_not_erase_InfoSale_when_already_set() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        OnSaleSeed onSaleSeed = OnSaleSeed.builder().willBeSoldDate(now).buyPrice(2).sellPrice(3).seedEnum(SeedEnum.BEETS).demand(new Demand(DemandType.VeryHighDemand, 5000)).build();
        HarvestablePlanted harvestablePlanted1 = new HarvestablePlanted(onSaleSeed, now, new InfoSale(10, 2, 10,10,true,10,10));
        var userInfo1 = new UserInfo("1", "greg.lol@mdr.fr", 200, 0, List.of(new HarvestableZone(HarvestableZoneType.ZONE_2, harvestablePlanted1)) );
        HarvestablePlanted harvestablePlanted2Zone1 = new HarvestablePlanted(onSaleSeed, now, new InfoSale(10, 2, 10,10,true,10,10));
        HarvestablePlanted harvestablePlanted2Zone2 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo2 = new UserInfo("2", "greg2.lol@mdr.fr", 200, 0, List.of(new HarvestableZone(HarvestableZoneType.ZONE_1, harvestablePlanted2Zone1), new HarvestableZone(HarvestableZoneType.ZONE_2, harvestablePlanted2Zone2)) );
        when(userInfoPort.findAll()).thenReturn(List.of(userInfo1, userInfo2));
        when(userInfoPort.countAll()).thenReturn(1L);

        resolveSales.execute(GrowthTime.SECOND_GROWTH_TIME);

        verify(userInfoPort).saveAll(userInfoCaptor.capture());
        List<UserInfo> value = userInfoCaptor.getValue();
        assertThat(value).containsExactlyInAnyOrder(
                new UserInfo("2", "greg2.lol@mdr.fr", 200, 0, List.of(
                        new HarvestableZone(HarvestableZoneType.ZONE_1, new HarvestablePlanted(onSaleSeed, now, new InfoSale(10, 2, 10,10,true,10,10))),
                        new HarvestableZone(HarvestableZoneType.ZONE_2, new HarvestablePlanted(onSaleSeed, now, new InfoSale(1, 11, 12, 12, true, 36, 12)))
                ))
        );
    }

    @Test
    void should_not_resolve_sales_when_onSaleSeed_is_not_old_enough() {
        //GIVEN
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        OnSaleSeed onSaleSeed = OnSaleSeed.builder().willBeSoldDate(now.plusMinutes(2)).buyPrice(2).sellPrice(3).seedEnum(SeedEnum.BEETS).demand(new Demand(DemandType.VeryHighDemand, 5000)).build();
        HarvestablePlanted harvestablePlanted1 = new HarvestablePlanted(onSaleSeed, now, null);
        var userInfo1 = new UserInfo("1", "greg.lol@mdr.fr", 200, 0, List.of(new HarvestableZone(HarvestableZoneType.ZONE_2, harvestablePlanted1)) );

        HarvestablePlanted harvestablePlanted2Zone1 = new HarvestablePlanted(onSaleSeed, now, null);
        OnSaleSeed onSaleSeed2 = OnSaleSeed.builder().willBeSoldDate(now).buyPrice(2).sellPrice(3).seedEnum(SeedEnum.BEETS).demand(new Demand(DemandType.VeryHighDemand, 5000)).build();
        HarvestablePlanted harvestablePlanted2Zone2 = new HarvestablePlanted(onSaleSeed2, now, null);
        var userInfo2 = new UserInfo("2", "greg2.lol@mdr.fr", 200, 0, List.of(new HarvestableZone(HarvestableZoneType.ZONE_1, harvestablePlanted2Zone1), new HarvestableZone(HarvestableZoneType.ZONE_2, harvestablePlanted2Zone2)) );

        when(userInfoPort.findAll()).thenReturn(List.of(userInfo1, userInfo2));
        when(userInfoPort.countAll()).thenReturn(1L);

        //WHEN
        resolveSales.execute(GrowthTime.SECOND_GROWTH_TIME);

        //THEN
        verify(userInfoPort).saveAll(userInfoCaptor.capture());
        List<UserInfo> value = userInfoCaptor.getValue();
        assertThat(value.get(0)).isEqualTo(
                new UserInfo("2", "greg2.lol@mdr.fr", 200, 0, List.of(
                        new HarvestableZone(HarvestableZoneType.ZONE_1, new HarvestablePlanted(onSaleSeed, now, null)),
                        new HarvestableZone(HarvestableZoneType.ZONE_2, new HarvestablePlanted(onSaleSeed2, now, new InfoSale(1, 11, 12, 12, true, 36, 12)))
                ))
        );
    }

}
