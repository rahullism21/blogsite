package com.blogsite.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API GATEWAY — Single Entry Point
 *
 * All client requests (from Angular or Postman) are sent to port 8080.
 * The gateway reads the request path and routes to the appropriate microservice:
 *
 *   /api/v1.0/blogsite/user/register        → user-service   (port 8081)
 *   /api/v1.0/blogsite/user/blogs/add/**    → blog-service   (port 8082)
 *   /api/v1.0/blogsite/user/delete/**       → blog-service   (port 8082)
 *   /api/v1.0/blogsite/user/getall          → blog-service   (port 8082)
 *   /api/v1.0/blogsite/user/blogs/update/** → blog-service   (port 8082)
 *   /api/v1.0/blogsite/blogs/info/**        → search-service (port 8083)
 *   /api/v1.0/blogsite/blogs/get/**         → search-service (port 8083)
 *
 * Service names are resolved via Eureka (lb:// load balancer scheme).
 * No hardcoded host:port — services are discovered dynamically.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
