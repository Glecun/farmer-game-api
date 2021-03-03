package com.glecun.farmergameapi.application;

import com.glecun.farmergameapi.application.dto.UserJson;
import com.glecun.farmergameapi.domain.ApplicationDomain;
import com.glecun.farmergameapi.domain.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationResource {

    final ApplicationDomain applicationDomain;

    @Autowired
    public AuthenticationResource(ApplicationDomain applicationDomain) {
        this.applicationDomain = applicationDomain;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody UserJson user) {
        applicationDomain.signUpUser(user.toUser());
    }

    @GetMapping ("/sign-in")
    public String signIn() {
        return "";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public UserJson currentUserName(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return UserJson.fromUser(user);
    }
}
