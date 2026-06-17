package com.travelagency.msseguimiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsSeguimientoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsSeguimientoApplication.class, args);
    }
}
