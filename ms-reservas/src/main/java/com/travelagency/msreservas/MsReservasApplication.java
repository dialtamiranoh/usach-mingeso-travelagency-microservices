package com.travelagency.msreservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class MsReservasApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsReservasApplication.class, args);
    }
}
