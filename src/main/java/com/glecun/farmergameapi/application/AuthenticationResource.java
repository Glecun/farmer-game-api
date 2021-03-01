package com.glecun.farmergameapi.application;

import com.glecun.farmergameapi.application.dto.UserJson;
import com.glecun.farmergameapi.domain.ApplicationDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
