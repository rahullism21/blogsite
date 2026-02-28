package com.blogsite.notificationservice.service;

import com.blogsite.notificationservice.model.Notification;
import com.blogsite.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    @Autowired private NotificationRepository notificationRepository;

    /** UML: sendNotification() — called internally by other services */
    public Notification sendNotification(Long userId, String message) {
        Notification n = Notification.builder()
                .userId(userId)
                .message(message)
                .isRead(false)
                .build();
        return notificationRepository.save(n);
    }

    /** UML: markAsRead() */
    public Notification markAsRead(Long notificationId) {
        Notification n = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + notificationId));
        n.setRead(true);
        return notificationRepository.save(n);
    }

    public List<Notification> getAllForUser(Long userId) {
        return notificationRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    public List<Notification> getUnreadForUser(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    public long countUnread(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }
}
