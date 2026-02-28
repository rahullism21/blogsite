package com.blogsite.notificationservice.controller;

import com.blogsite.notificationservice.model.Notification;
import com.blogsite.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * UML Methods:
 *   sendNotification() → POST  /api/v1.0/blogsite/notifications/send
 *   markAsRead()       → PUT   /api/v1.0/blogsite/notifications/read/{id}
 *   getAll             → GET   /api/v1.0/blogsite/notifications/user/{userId}
 *   getUnread          → GET   /api/v1.0/blogsite/notifications/unread/{userId}
 */
@RestController
@RequestMapping("/api/v1.0/blogsite/notifications")
public class NotificationController {

    @Autowired private NotificationService notificationService;

    /** UML: sendNotification() */
    @PostMapping("/send")
    public ResponseEntity<Notification> sendNotification(@RequestParam Long userId,
                                                          @RequestParam String message) {
        return new ResponseEntity<>(
                notificationService.sendNotification(userId, message),
                HttpStatus.CREATED);
    }

    /** UML: markAsRead() */
    @PutMapping("/read/{notificationId}")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long notificationId) {
        return ResponseEntity.ok(notificationService.markAsRead(notificationId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getAllForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getAllForUser(userId));
    }

    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<Notification>> getUnreadForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUnreadForUser(userId));
    }

    @GetMapping("/unread/count/{userId}")
    public ResponseEntity<Long> countUnread(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.countUnread(userId));
    }
}
