package com.glecun.farmergameapi.application;

import com.glecun.farmergameapi.application.dto.HarvestableZoneJson;
import com.glecun.farmergameapi.application.dto.MarketInfoJson;
import com.glecun.farmergameapi.application.dto.UserInfoJson;
import com.glecun.farmergameapi.application.dto.UserJson;
import com.glecun.farmergameapi.domain.ApplicationDomain;
import com.glecun.farmergameapi.domain.entities.HarvestableZoneType;
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
    public UserInfoJson plantInAZone(@RequestBody HarvestableZoneJson harvestableZoneJson, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return UserInfoJson.from(applicationDomain.plantInAZone(harvestableZoneJson.toHarvestableZone(), user));
    }

    @PostMapping("/acknowledge-infosale/")
    public UserInfoJson acknowledgeInfoSale(@RequestBody HarvestableZoneJson harvestableZoneJson, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return UserInfoJson.from(applicationDomain.acknowledgeInfoSales(harvestableZoneJson.toHarvestableZone(), user));
    }

    @PostMapping("/unlock-zone/")
    public UserInfoJson unlockZone(@RequestBody String harvestableZoneType, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return UserInfoJson.from(applicationDomain.unlockHarvestableZone(user, HarvestableZoneType.valueOf(harvestableZoneType)));
    }
}
