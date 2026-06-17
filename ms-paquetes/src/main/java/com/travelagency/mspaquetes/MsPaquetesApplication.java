package com.travelagency.mspaquetes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsPaquetesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsPaquetesApplication.class, args);
    }
}
