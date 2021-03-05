package com.glecun.farmergameapi.application;

import com.glecun.farmergameapi.application.dto.MarketInfoJson;
import com.glecun.farmergameapi.application.dto.UserInfoJson;
import com.glecun.farmergameapi.application.dto.UserJson;
import com.glecun.farmergameapi.domain.ApplicationDomain;
import com.glecun.farmergameapi.domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
