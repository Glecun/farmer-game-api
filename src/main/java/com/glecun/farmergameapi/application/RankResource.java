package com.glecun.farmergameapi.application;

import com.glecun.farmergameapi.application.dto.MarketInfoJson;
import com.glecun.farmergameapi.application.dto.RanksInfoJson;
import com.glecun.farmergameapi.domain.ApplicationDomain;
import com.glecun.farmergameapi.domain.entities.RanksInfo;
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
    public RanksInfoJson getRanksInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return RanksInfoJson.from(applicationDomain.getRanksInfo(user));
    }
}
