package com.travelagency.mspagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
public class MsPagosApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsPagosApplication.class, args);
    }
}
