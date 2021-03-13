package com.glecun.farmergameapi.domain;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.glecun.farmergameapi.domain.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.glecun.farmergameapi.domain.port.UserInfoPort;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ApplicationDomainTest {

   @Mock
   private UserInfoPort userInfoPort;
   @Mock
   private GetCurrentMarketInfo getCurrentMarketInfo;

   @InjectMocks
   ApplicationDomain applicationDomain;

   @Test
   void should_create_default_user_info_when_inexitant() {
      var email = "greg.lol@mdr.fr";
      when(userInfoPort.findByEmail(email)).thenReturn(Optional.empty());

      applicationDomain.getUserInfo(email);

      var harvestableZones = Arrays.stream(HarvestableZoneType.values())
            .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null, harvestableZoneType.lockedByDefault))
            .collect(Collectors.toList());
      var expectedUserInfo = new UserInfo(
            null,
            email,
            200,
            0,
            harvestableZones,
            emptyList()
      );

      verify(userInfoPort).save(expectedUserInfo);
   }

   @Test
   void should_plant_in_a_zone() {
      LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
      Supplier<LocalDateTime> nowSupplier = () -> now;
      ReflectionTestUtils.setField(applicationDomain, "now", nowSupplier);
      var user = new User("","greg.lol@mdr.fr","" );
      var harvestableZones = Arrays.stream(HarvestableZoneType.values())
              .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null, harvestableZoneType.lockedByDefault))
              .collect(Collectors.toList());
      when(userInfoPort.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserInfo("1", "greg.lol@mdr.fr", 200, 0, harvestableZones, singletonList(SeedEnum.GREEN_BEAN))));
      when(userInfoPort.save(any())).thenReturn(new UserInfo("1", "greg.lol@mdr.fr", 200, 0, harvestableZones, Collections.emptyList()));

      OnSaleSeed seedsPlanted = OnSaleSeed.builder()
              .seedEnum(SeedEnum.GREEN_BEAN)
              .buyPrice(5)
              .sellPrice(10)
              .demand(new Demand(DemandType.SmallDemand, 5))
              .onSaleDate(null)
              .willBeSoldDate(now)
              .build();
      when(getCurrentMarketInfo.execute()).thenReturn(Optional.of(new MarketInfo("1", List.of(seedsPlanted), now)));

      applicationDomain.plantInAZone(HarvestableZoneType.ZONE_1,SeedEnum.GREEN_BEAN, user);

      var expectedHarvestableZones = Arrays.stream(HarvestableZoneType.values())
              .map(harvestableZoneType -> {
                 if (harvestableZoneType.equals(HarvestableZoneType.ZONE_1)){
                    return new HarvestableZone(harvestableZoneType, new HarvestablePlanted(seedsPlanted, now, null), harvestableZoneType.lockedByDefault);
                 }
                 return new HarvestableZone(harvestableZoneType, null, harvestableZoneType.lockedByDefault);
              })
              .collect(Collectors.toList());
      var expectedUserInfo = new UserInfo(
              "1",
              "greg.lol@mdr.fr",
              140,
            0,
            expectedHarvestableZones,
            singletonList(SeedEnum.GREEN_BEAN)
      );

      verify(userInfoPort).save(expectedUserInfo);
   }

   @Test
   void should_plant_in_a_zone_and_keep_min_money() {
      LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
      Supplier<LocalDateTime> nowSupplier = () -> now;
      ReflectionTestUtils.setField(applicationDomain, "now", nowSupplier);
      var user = new User("","greg.lol@mdr.fr","" );
      var harvestableZones = Arrays.stream(HarvestableZoneType.values())
              .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null, harvestableZoneType.lockedByDefault))
              .collect(Collectors.toList());
      when(userInfoPort.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserInfo("1", "greg.lol@mdr.fr", 200, 0, harvestableZones, singletonList(SeedEnum.GREEN_BEAN))));
      when(userInfoPort.save(any())).thenReturn(new UserInfo("1", "greg.lol@mdr.fr", 200, 0, harvestableZones, Collections.emptyList()));

      OnSaleSeed seedsPlanted = OnSaleSeed.builder()
              .seedEnum(SeedEnum.GREEN_BEAN)
              .buyPrice(14)
              .sellPrice(10)
              .demand(new Demand(DemandType.SmallDemand, 5))
              .onSaleDate(null)
              .willBeSoldDate(now)
              .build();
      when(getCurrentMarketInfo.execute()).thenReturn(Optional.of(new MarketInfo("1", List.of(seedsPlanted), now)));

      applicationDomain.plantInAZone(HarvestableZoneType.ZONE_1, SeedEnum.GREEN_BEAN , user);

      var expectedHarvestableZones = Arrays.stream(HarvestableZoneType.values())
              .map(harvestableZoneType -> {
                 if (harvestableZoneType.equals(HarvestableZoneType.ZONE_1)){
                    return new HarvestableZone(harvestableZoneType, new HarvestablePlanted(seedsPlanted, now, null), harvestableZoneType.lockedByDefault);
                 }
                 return new HarvestableZone(harvestableZoneType, null, harvestableZoneType.lockedByDefault);
              })
              .collect(Collectors.toList());
      var expectedUserInfo = new UserInfo(
              "1",
              "greg.lol@mdr.fr",
              100,
              0,
              expectedHarvestableZones,
              singletonList(SeedEnum.GREEN_BEAN));

      verify(userInfoPort).save(expectedUserInfo);
   }

   @Test
   void should_not_plant_in_a_zone_when_no_UserInfo() {
      var user = new User("","greg.lol@mdr.fr","" );
      when(getCurrentMarketInfo.execute()).thenReturn(Optional.of(new MarketInfo("1", List.of(OnSaleSeed.builder().seedEnum(SeedEnum.GREEN_BEAN).build()), LocalDateTime.now(ZoneOffset.UTC))));
      when(userInfoPort.findByEmail(user.getEmail())).thenReturn(Optional.empty());
      assertThatThrownBy(() -> applicationDomain.plantInAZone(null,SeedEnum.GREEN_BEAN, user)).isInstanceOf(RuntimeException.class);
   }

   @Test
   void should_not_plant_in_a_zone_when_seed_locked() {
      LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
      Supplier<LocalDateTime> nowSupplier = () -> now;
      ReflectionTestUtils.setField(applicationDomain, "now", nowSupplier);
      var user = new User("","greg.lol@mdr.fr","" );
      var harvestableZones = Arrays.stream(HarvestableZoneType.values())
              .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null, harvestableZoneType.lockedByDefault))
              .collect(Collectors.toList());
      when(userInfoPort.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserInfo("1", "greg.lol@mdr.fr", 200, 0, harvestableZones, singletonList(SeedEnum.ASPARAGUS))));

      OnSaleSeed seedsPlanted = OnSaleSeed.builder()
              .seedEnum(SeedEnum.GREEN_BEAN)
              .buyPrice(14)
              .sellPrice(10)
              .demand(new Demand(DemandType.SmallDemand, 5))
              .onSaleDate(null)
              .willBeSoldDate(now)
              .build();
      when(getCurrentMarketInfo.execute()).thenReturn(Optional.of(new MarketInfo("1", List.of(seedsPlanted), now)));

      assertThatThrownBy(() ->applicationDomain.plantInAZone(HarvestableZoneType.ZONE_1, SeedEnum.GREEN_BEAN , user)).isInstanceOf(RuntimeException.class);
   }

   @Test
   void should_not_plant_in_a_zone_when_no_OnSaleSeed() {
      var user = new User("","greg.lol@mdr.fr","" );
      when(getCurrentMarketInfo.execute()).thenReturn(Optional.of(new MarketInfo("1", emptyList(), LocalDateTime.now(ZoneOffset.UTC))));

      assertThatThrownBy(() -> applicationDomain.plantInAZone(HarvestableZoneType.ZONE_1, SeedEnum.GREEN_BEAN, user)).isInstanceOf(RuntimeException.class);
   }

   @Test
   void should_fail_when_plant_in_a_zone_and_no_UserInfo_returned_from_infrastructure() {
      var user = new User("","greg.lol@mdr.fr","" );
      var harvestableZones = Arrays.stream(HarvestableZoneType.values())
              .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null, harvestableZoneType.lockedByDefault))
              .collect(Collectors.toList());
      when(userInfoPort.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserInfo("1", "greg.lol@mdr.fr", 200, 0, harvestableZones, Collections.emptyList())));
      OnSaleSeed seedsPlanted = OnSaleSeed.builder()
              .seedEnum(SeedEnum.GREEN_BEAN)
              .buyPrice(5)
              .sellPrice(10)
              .demand(new Demand(DemandType.SmallDemand, 5))
              .onSaleDate(null)
              .willBeSoldDate(LocalDateTime.now(ZoneOffset.UTC))
              .build();
      when(getCurrentMarketInfo.execute()).thenReturn(Optional.of(new MarketInfo("1", List.of(seedsPlanted), LocalDateTime.now(ZoneOffset.UTC))));

      assertThatThrownBy(() -> applicationDomain.plantInAZone(HarvestableZoneType.ZONE_1, SeedEnum.GREEN_BEAN, user)).isInstanceOf(RuntimeException.class);
   }

   @Test
   void should_not_plant_in_a_zone_when_money_negative() {
      var user = new User("","greg.lol@mdr.fr","" );
      var harvestableZones = Arrays.stream(HarvestableZoneType.values())
              .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null, harvestableZoneType.lockedByDefault))
              .collect(Collectors.toList());
      when(userInfoPort.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserInfo("1", "greg.lol@mdr.fr", 100, 0, harvestableZones, Collections.emptyList())));
      OnSaleSeed seedsPlanted = OnSaleSeed.builder()
              .seedEnum(SeedEnum.GREEN_BEAN)
              .buyPrice(200)
              .sellPrice(10)
              .demand(new Demand(DemandType.SmallDemand, 5))
              .onSaleDate(null)
              .willBeSoldDate(LocalDateTime.now(ZoneOffset.UTC))
              .build();
      LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
      when(getCurrentMarketInfo.execute()).thenReturn(Optional.of(new MarketInfo("1", List.of(seedsPlanted), now)));

      assertThatThrownBy(() -> applicationDomain.plantInAZone(HarvestableZoneType.ZONE_1, SeedEnum.GREEN_BEAN, user)).isInstanceOf(RuntimeException.class);

   }

   @Test
   void should_acknowledge_infosale() {
      OnSaleSeed seedsPlanted = OnSaleSeed.builder()
            .seedEnum(SeedEnum.GREEN_BEAN)
            .buyPrice(5)
            .sellPrice(10)
            .demand(new Demand(DemandType.SmallDemand, 5))
            .onSaleDate(null)
            .willBeSoldDate(LocalDateTime.now(ZoneOffset.UTC))
            .build();
      HarvestableZone harvestableZone = new HarvestableZone(
            HarvestableZoneType.ZONE_1,
            new HarvestablePlanted(
                  seedsPlanted,
                  LocalDateTime.now(ZoneOffset.UTC),
                  new InfoSale(5, 1, 25, 5, true, 10, 5 )
            ),
            false
      );
      var user = new User("grewa", "greg.lol@mdr.fr", "pass");
      when(userInfoPort.findByEmail("greg.lol@mdr.fr")).thenReturn(Optional.of(
            new UserInfo("1", "greg.lol@mdr.fr", 200, 0, singletonList(harvestableZone), Collections.emptyList())
      ));
      when(userInfoPort.save(any())).thenReturn(new UserInfo("osef", "osef", 200, 0, emptyList(), Collections.emptyList()));

      applicationDomain.acknowledgeInfoSales(HarvestableZoneType.ZONE_1, user);

      var expectedUserInfo = new UserInfo(
            "1",
            "greg.lol@mdr.fr",
            210,
            5,
            singletonList(new HarvestableZone(HarvestableZoneType.ZONE_1, null, false)),
              Collections.emptyList());
      verify(userInfoPort).save(expectedUserInfo);
   }

   @Test
   void should_unlock_HarvestableZone() {
      var user = new User("grewa", "greg.lol@mdr.fr", "pass");
      HarvestableZone harvestableZone1 = new HarvestableZone(HarvestableZoneType.ZONE_1, null, false);
      HarvestableZone harvestableZone3 = new HarvestableZone(HarvestableZoneType.ZONE_3, null, true);
      when(userInfoPort.findByEmail("greg.lol@mdr.fr")).thenReturn(Optional.of(
            new UserInfo("1", "greg.lol@mdr.fr", 5000, 0, List.of(harvestableZone1, harvestableZone3), Collections.emptyList())
      ));
      when(userInfoPort.save(any())).thenReturn(new UserInfo("osef", "osef", 200, 0, emptyList(), Collections.emptyList()));

      applicationDomain.unlockHarvestableZone(user, HarvestableZoneType.ZONE_3);

      var expectedUserInfo = new UserInfo(
            "1",
            "greg.lol@mdr.fr",
            3000,
            0,
            List.of(
                  new HarvestableZone(HarvestableZoneType.ZONE_1, null, false),
                  new HarvestableZone(HarvestableZoneType.ZONE_3, null, false)
            ),
            Collections.emptyList()
      );
      verify(userInfoPort).save(expectedUserInfo);
   }

   @Test
   void should_not_unlock_HarvestableZone_when_not_enough_money() {
      var user = new User("grewa", "greg.lol@mdr.fr", "pass");
      HarvestableZone harvestableZone1 = new HarvestableZone(HarvestableZoneType.ZONE_1, null, false);
      HarvestableZone harvestableZone3 = new HarvestableZone(HarvestableZoneType.ZONE_3, null, true);
      when(userInfoPort.findByEmail("greg.lol@mdr.fr")).thenReturn(Optional.of(
            new UserInfo("1", "greg.lol@mdr.fr", 100, 0, List.of(harvestableZone1, harvestableZone3), Collections.emptyList())
      ));

      assertThatThrownBy(() -> applicationDomain.unlockHarvestableZone(user, HarvestableZoneType.ZONE_3)).isInstanceOf(RuntimeException.class);
   }

   @Test
   void should_not_unlock_HarvestableZone_when_already_unlocked() {
      var user = new User("grewa", "greg.lol@mdr.fr", "pass");
      HarvestableZone harvestableZone1 = new HarvestableZone(HarvestableZoneType.ZONE_1, null, false);
      HarvestableZone harvestableZone3 = new HarvestableZone(HarvestableZoneType.ZONE_3, null, false);
      when(userInfoPort.findByEmail("greg.lol@mdr.fr")).thenReturn(Optional.of(
              new UserInfo("1", "greg.lol@mdr.fr", 4000, 0, List.of(harvestableZone1, harvestableZone3), Collections.emptyList())
      ));

      assertThatThrownBy(() -> applicationDomain.unlockHarvestableZone(user, HarvestableZoneType.ZONE_3)).isInstanceOf(RuntimeException.class);
   }

   @Test
   void should_unlock_seed() {
      var user = new User("grewa", "greg.lol@mdr.fr", "pass");
      when(userInfoPort.findByEmail("greg.lol@mdr.fr")).thenReturn(Optional.of(
              new UserInfo("1", "greg.lol@mdr.fr", 500, 0, emptyList(), Collections.emptyList())
      ));
      when(userInfoPort.save(any())).thenReturn(new UserInfo("osef", "osef", 200, 0, emptyList(), Collections.emptyList()));

      applicationDomain.unlockSeed(user, SeedEnum.ASPARAGUS);

      var expectedUserInfo = new UserInfo(
              "1",
              "greg.lol@mdr.fr",
              490,
              0,
              emptyList(),
              singletonList(SeedEnum.ASPARAGUS)
      );
      verify(userInfoPort).save(expectedUserInfo);
   }

   @Test
   void should_not_unlock_seed_when_already_have_it() {
      var user = new User("grewa", "greg.lol@mdr.fr", "pass");
      when(userInfoPort.findByEmail("greg.lol@mdr.fr")).thenReturn(Optional.of(
              new UserInfo("1", "greg.lol@mdr.fr", 500, 0, emptyList(), List.of(SeedEnum.ASPARAGUS))
      ));

      assertThatThrownBy(() -> applicationDomain.unlockSeed(user, SeedEnum.ASPARAGUS)).isInstanceOf(RuntimeException.class);
   }

   @Test
   void should_not_unlock_seed_when_not_enough_money() {
      var user = new User("grewa", "greg.lol@mdr.fr", "pass");
      when(userInfoPort.findByEmail("greg.lol@mdr.fr")).thenReturn(Optional.of(
              new UserInfo("1", "greg.lol@mdr.fr", 9, 0, emptyList(), Collections.emptyList())
      ));

      assertThatThrownBy(() -> applicationDomain.unlockSeed(user, SeedEnum.ASPARAGUS)).isInstanceOf(RuntimeException.class);
   }
}
