package com.glecun.farmergameapi.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusResource {

    @GetMapping("/status")
    public String status() {
        return "RUNNING";
    }

}