package com.glecun.farmergameapi.application;

import com.glecun.farmergameapi.application.dto.*;
import com.glecun.farmergameapi.application.dto.post.HarvestableZoneTypeJson;
import com.glecun.farmergameapi.application.dto.post.PlantInAZonePostJson;
import com.glecun.farmergameapi.application.dto.post.SeedEnumJson;
import com.glecun.farmergameapi.domain.ApplicationDomain;
import com.glecun.farmergameapi.domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user-info")
public class UserInfoResource {
    private final ApplicationDomain applicationDomain;

    @Autowired
    public UserInfoResource(ApplicationDomain applicationDomain) {
        this.applicationDomain = applicationDomain;
    }


    @GetMapping("/")
    public UserInfoJson getUserInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return UserInfoJson.from(applicationDomain.getUserInfo(user.getEmail()));

    }

    @PostMapping("/plant-in-a-zone/")
    public UserInfoJson plantInAZone(@RequestBody PlantInAZonePostJson plantInAZonePostJson, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return UserInfoJson.from(applicationDomain.plantInAZone(
                plantInAZonePostJson.harvestableZoneTypeJson.harvestableZoneType,
                plantInAZonePostJson.seedEnumJson.seedEnum,
                user
       ));
    }

    @PostMapping("/acknowledge-infosale/")
    public UserInfoJson acknowledgeInfoSale(@RequestBody HarvestableZoneTypeJson harvestableZoneTypeJson, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return UserInfoJson.from(applicationDomain.acknowledgeInfoSales(harvestableZoneTypeJson.harvestableZoneType, user));
    }

    @PostMapping("/unlock-zone/")
    public UserInfoJson unlockZone(@RequestBody HarvestableZoneTypeJson harvestableZoneTypeJson , Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return UserInfoJson.from(applicationDomain.unlockHarvestableZone(user, harvestableZoneTypeJson.harvestableZoneType));
    }
}
