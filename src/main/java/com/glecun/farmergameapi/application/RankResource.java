package com.glecun.farmergameapi.application;

import com.glecun.farmergameapi.application.dto.RanksInfoByTiersJson;
import com.glecun.farmergameapi.domain.ApplicationDomain;
import com.glecun.farmergameapi.domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rank")
public class RankResource {
    private final ApplicationDomain applicationDomain;

    @Autowired
    public RankResource(ApplicationDomain applicationDomain) {
        this.applicationDomain = applicationDomain;
    }

    @GetMapping("/")
    public RanksInfoByTiersJson getRanksInfoByTiers(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return RanksInfoByTiersJson.from(applicationDomain.getRanksInfoByTiers(user));
    }
}
