package com.glecun.farmergameapi.domain;

import com.glecun.farmergameapi.domain.port.UserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class CheckUserCredentials implements UserDetailsService {

    private final UserPort userPort;

    public CheckUserCredentials(@Autowired UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userPort.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email)));
    }
}
