package com.blogsite.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * USER MICROSERVICE
 *
 * Responsibilities:
 *   - User registration (US01)
 *   - Owns and manages the 'users_db' database exclusively
 *   - Exposes internal API for other services to lookup user details
 *
 * Runs on port: 8081
 * Registers with Eureka as: "user-service"
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
