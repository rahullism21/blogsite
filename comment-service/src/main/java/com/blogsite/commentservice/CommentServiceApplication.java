package com.blogsite.commentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * COMMENT MICROSERVICE
 * UML Entity: Comment — commentId, content, author:User, post:BlogPost, timestamp
 * Methods: addComment(), editComment(), deleteComment()
 * Port: 8084 | DB: comments_db
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CommentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentServiceApplication.class, args);
    }
}
