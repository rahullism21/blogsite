package com.blogsite.blogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * BLOG MICROSERVICE
 *
 * Responsibilities:
 *   - Add, delete, update, view user's blogs (US02, US03)
 *   - Owns and manages the 'blogs_db' database exclusively
 *   - Calls user-service via Feign for user verification (no shared DB)
 *
 * Runs on port: 8082
 * Registers with Eureka as: "blog-service"
 * Calls: user-service (via Feign)
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BlogServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogServiceApplication.class, args);
    }
}
