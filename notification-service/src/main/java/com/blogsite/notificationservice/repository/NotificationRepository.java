package com.blogsite.notificationservice.repository;

import com.blogsite.notificationservice.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdOrderByTimestampDesc(Long userId);
    List<Notification> findByUserIdAndIsReadFalse(Long userId);
    long countByUserIdAndIsReadFalse(Long userId);
}
