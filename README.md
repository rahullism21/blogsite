# Blog Site Application - Microservices (Updated from UML Diagram)

## Services: 7 independent Spring Boot apps

| Service              | Port | Database         | UML Entities                                   |
|----------------------|------|------------------|------------------------------------------------|
| eureka-server        | 8761 | none             | Service Registry                               |
| api-gateway          | 8080 | none             | Routes all requests                            |
| user-service         | 8081 | users_db         | User: register,login,updateProfile,delete      |
| blog-service         | 8082 | blogs_db         | BlogPost,Category,Tag,Like                     |
| comment-service      | 8084 | comments_db      | Comment: add,edit,delete                       |
| notification-service | 8085 | notifications_db | Notification: send,markAsRead                  |
| search-service       | 8083 | blogs_db (read)  | Search by category/tag/duration + Builder      |

## Startup Order
1. eureka-server  2. api-gateway  3. user-service
4. blog-service   5. comment-service  6. notification-service  7. search-service

## Databases
users_db → user-service | blogs_db → blog-service+search-service(read)
comments_db → comment-service | notifications_db → notification-service
