package com.blogsite.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * EUREKA SERVICE REGISTRY
 *
 * This is the Service Discovery server for the Blog Site microservices ecosystem.
 * All microservices (user-service, blog-service, search-service, api-gateway)
 * register themselves here on startup. They use Eureka to discover each other
 * without hardcoded hostnames or ports.
 *
 * Dashboard: http://localhost:8761
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
