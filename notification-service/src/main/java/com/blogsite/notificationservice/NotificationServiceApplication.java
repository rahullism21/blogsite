package com.blogsite.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * NOTIFICATION MICROSERVICE
 * UML Entity: Notification — notificationId, user:User, message, timestamp
 * Methods: sendNotification(), markAsRead()
 * Port: 8085 | DB: notifications_db
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
