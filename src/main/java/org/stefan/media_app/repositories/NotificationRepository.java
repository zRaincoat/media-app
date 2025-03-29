package org.stefan.media_app.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.stefan.media_app.models.Notification;
import org.stefan.media_app.models.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    @Query("""
            SELECT n
            FROM Notification n
            WHERE n.user = :user
              AND n.deletedAt IS NULL
            ORDER BY n.createdAt
            """)
    Page<Notification> getAllNotificationsByUser(User user, Pageable pageable);

    @Query("""
            SELECT n
            FROM Notification n
            WHERE n IN :notifications
            """)
    List<Notification> fetchAllWithVideos(List<Notification> notifications);

    @Modifying
    @Query(value = """
            UPDATE notifications
            SET is_read = TRUE
            WHERE id IN (:notificationsIds)
              AND deleted_at IS NULL
            """, nativeQuery = true)
    void setAllNotificationsIsReadTrue(List<UUID> notificationsIds);
}
