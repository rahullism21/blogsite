package com.blogsite.searchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * SEARCH MICROSERVICE
 *
 * Responsibilities:
 *   - Search blogs by category (US04)
 *   - Search blogs by category + date range (US04) — Builder pattern applied here
 *   - Calls user-service via Feign to enrich responses with author details
 *   - Reads from blogs_db (read-only, ddl-auto=validate)
 *
 * Runs on port: 8083
 * Registers with Eureka as: "search-service"
 * Calls: user-service (via Feign)
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SearchServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchServiceApplication.class, args);
    }
}
