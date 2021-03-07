package com.glecun.farmergameapi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.glecun.farmergameapi.domain.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
      when(userInfoPort.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserInfo("1", "greg.lol@mdr.fr", 200, harvestableZones )));
      when(userInfoPort.save(any())).thenReturn(new UserInfo("1", "greg.lol@mdr.fr", 200, harvestableZones ));
      OnSaleSeed seedsPlanted = OnSaleSeed.builder()
              .seedEnum(SeedEnum.GREEN_BEAN)
              .buyPrice(5)
              .sellPrice(10)
              .demand(new Demand(DemandType.SmallDemand, 5))
              .onSaleDate(null)
              .willBeSoldDate(LocalDateTime.now())
              .build();
      LocalDateTime now = LocalDateTime.now();
      HarvestableZone harvestableZone = new HarvestableZone(
              HarvestableZoneType.ZONE_1,
              new HarvestablePlanted(seedsPlanted, now, null)
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
              195,
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
      when(userInfoPort.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserInfo("1", "greg.lol@mdr.fr", 200, harvestableZones )));
      HarvestableZone harvestableZone = new HarvestableZone(HarvestableZoneType.ZONE_1, null);

      assertThatThrownBy(() -> applicationDomain.plantInAZone(harvestableZone, user)).isInstanceOf(RuntimeException.class);
   }

   @Test
   void should_fail_when_plant_in_a_zone_and_no_UserInfo_returned_from_infrastructure() {
      var user = new User("","greg.lol@mdr.fr","" );
      var harvestableZones = Arrays.stream(HarvestableZoneType.values())
              .map(harvestableZoneType -> new HarvestableZone(harvestableZoneType, null))
              .collect(Collectors.toList());
      when(userInfoPort.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserInfo("1", "greg.lol@mdr.fr", 200, harvestableZones )));
      OnSaleSeed seedsPlanted = OnSaleSeed.builder()
              .seedEnum(SeedEnum.GREEN_BEAN)
              .buyPrice(5)
              .sellPrice(10)
              .demand(new Demand(DemandType.SmallDemand, 5))
              .onSaleDate(null)
              .willBeSoldDate(LocalDateTime.now())
              .build();
      HarvestableZone harvestableZone = new HarvestableZone(
              HarvestableZoneType.ZONE_1,
              new HarvestablePlanted(seedsPlanted, LocalDateTime.now(), null)
      );

      assertThatThrownBy(() -> applicationDomain.plantInAZone(harvestableZone, user)).isInstanceOf(RuntimeException.class);
   }
}
