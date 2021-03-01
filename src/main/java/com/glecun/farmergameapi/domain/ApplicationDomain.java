package com.glecun.farmergameapi.domain;

import com.glecun.farmergameapi.domain.entities.User;
import com.glecun.farmergameapi.domain.port.UserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationDomain {

    private final SignUp signUp;

    @Autowired
    public ApplicationDomain(SignUp signUp) {
        this.signUp = signUp;
    }

    public void signUpUser(User user) {
        signUp.execute(user);
    }
}
