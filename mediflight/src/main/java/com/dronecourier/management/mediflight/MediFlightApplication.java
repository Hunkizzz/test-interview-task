package com.dronecourier.management.mediflight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MediFlightApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediFlightApplication.class, args);
    }
}
