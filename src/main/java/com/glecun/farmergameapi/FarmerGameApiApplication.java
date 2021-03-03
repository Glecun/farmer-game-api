package com.glecun.farmergameapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FarmerGameApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmerGameApiApplication.class, args);
	}

}
