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
import java.util.Optional;
import java.util.stream.Collectors;

import com.glecun.farmergameapi.domain.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.glecun.farmergameapi.domain.port.UserInfoPort;

@ExtendWith(MockitoExtension.class)
class ApplicationDomainTest {

   @Mock
   private UserInfoPort userInfoPort;

   @InjectMocks
   ApplicationDomain applicationDomain;

   @Test
   void should_create_default_user_info_when_inexitant() {
      var email = "greg.lol@mdr.fr";
      when(userInfoPort.findByEmail(email)).thenReturn(Optional.empty());

      applicationDomain.getUserInfo(email);

      var harvestableZones = Arrays.stream(HarvestableZoneType.values())
            .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null))
            .collect(Collectors.toList());
      var expectedUserInfo = new UserInfo(
              null,
              email,
            200,
            0,
            harvestableZones
      );

      verify(userInfoPort).save(expectedUserInfo);
   }

   @Test
   void should_plant_in_a_zone() {
      var user = new User("","greg.lol@mdr.fr","" );
      var harvestableZones = Arrays.stream(HarvestableZoneType.values())
              .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null))
              .collect(Collectors.toList());
      when(userInfoPort.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserInfo("1", "greg.lol@mdr.fr", 200, 0, harvestableZones )));
      when(userInfoPort.save(any())).thenReturn(new UserInfo("1", "greg.lol@mdr.fr", 200, 0, harvestableZones ));
      OnSaleSeed seedsPlanted = OnSaleSeed.builder()
              .seedEnum(SeedEnum.GREEN_BEAN)
              .buyPrice(5)
              .sellPrice(10)
              .demand(new Demand(DemandType.SmallDemand, 5))
              .onSaleDate(null)
              .willBeSoldDate(LocalDateTime.now(ZoneOffset.UTC))
              .build();
      LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
      InfoSale infoSaleSendByUnity = new InfoSale(0, 0, 0, false, 0, 0);
      HarvestableZone harvestableZone = new HarvestableZone(
              HarvestableZoneType.ZONE_1,
              new HarvestablePlanted(seedsPlanted, now, infoSaleSendByUnity)
      );

      applicationDomain.plantInAZone(harvestableZone, user);

      var expectedHarvestableZones = Arrays.stream(HarvestableZoneType.values())
              .map(harvestableZoneType -> {
                 if (harvestableZoneType.equals(HarvestableZoneType.ZONE_1)){
                    return new HarvestableZone(harvestableZoneType, new HarvestablePlanted(seedsPlanted, now, null));
                 }
                 return new HarvestableZone(harvestableZoneType, null);
              })
              .collect(Collectors.toList());
      var expectedUserInfo = new UserInfo(
              "1",
              "greg.lol@mdr.fr",
              140,
            0,
            expectedHarvestableZones
      );

      verify(userInfoPort).save(expectedUserInfo);
   }

   @Test
   void should_not_plant_in_a_zone_when_no_UserInfo() {
      var user = new User("","greg.lol@mdr.fr","" );

      when(userInfoPort.findByEmail(user.getEmail())).thenReturn(Optional.empty());
      assertThatThrownBy(() -> applicationDomain.plantInAZone(null, user)).isInstanceOf(RuntimeException.class);

   }

   @Test
   void should_not_plant_in_a_zone_when_no_OnSaleSeed() {
      var user = new User("","greg.lol@mdr.fr","" );
      var harvestableZones = Arrays.stream(HarvestableZoneType.values())
              .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null))
              .collect(Collectors.toList());
      when(userInfoPort.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserInfo("1", "greg.lol@mdr.fr", 200, 0, harvestableZones )));
      HarvestableZone harvestableZone = new HarvestableZone(HarvestableZoneType.ZONE_1, null);

      assertThatThrownBy(() -> applicationDomain.plantInAZone(harvestableZone, user)).isInstanceOf(RuntimeException.class);
   }

   @Test
   void should_fail_when_plant_in_a_zone_and_no_UserInfo_returned_from_infrastructure() {
      var user = new User("","greg.lol@mdr.fr","" );
      var harvestableZones = Arrays.stream(HarvestableZoneType.values())
              .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null))
              .collect(Collectors.toList());
      when(userInfoPort.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserInfo("1", "greg.lol@mdr.fr", 200, 0, harvestableZones )));
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
              new HarvestablePlanted(seedsPlanted, LocalDateTime.now(ZoneOffset.UTC), null)
      );

      assertThatThrownBy(() -> applicationDomain.plantInAZone(harvestableZone, user)).isInstanceOf(RuntimeException.class);
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
                  new InfoSale(5, 25, 5, true, 10, 5 )
            )
      );
      var user = new User("grewa", "greg.lol@mdr.fr", "pass");
      when(userInfoPort.findByEmail("greg.lol@mdr.fr")).thenReturn(Optional.of(
            new UserInfo("1", "greg.lol@mdr.fr", 200, 0, singletonList(harvestableZone))
      ));
      when(userInfoPort.save(any())).thenReturn(new UserInfo("osef", "osef", 200, 0, emptyList()));

      applicationDomain.acknowledgeInfoSales(harvestableZone, user);

      var expectedUserInfo = new UserInfo(
            "1",
            "greg.lol@mdr.fr",
            210,
            5,
            singletonList(new HarvestableZone(HarvestableZoneType.ZONE_1, null))
      );
      verify(userInfoPort).save(expectedUserInfo);
   }
}
