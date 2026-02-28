package com.blogsite.notificationservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * UML Entity: Notification
 * Fields  : notificationId, user: User, message: string, timestamp: DateTime
 * Methods : sendNotification(), markAsRead()
 *
 * user → stored as userId (Long) — cross-service reference
 */
@Entity
@Table(name = "notifications")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    /** user: User — cross-service reference */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "message", nullable = false, length = 500)
    private String message;

    /** UML field: timestamp: DateTime */
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    /** UML method markAsRead() toggles this */
    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @PrePersist
    protected void onCreate() { timestamp = LocalDateTime.now(); }
}
