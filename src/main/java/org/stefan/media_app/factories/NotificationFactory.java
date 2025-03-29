package org.stefan.media_app.factories;

import org.stefan.media_app.enums.NotificationType;
import org.stefan.media_app.models.Notification;
import org.stefan.media_app.models.User;
import org.stefan.media_app.models.Video;

public class NotificationFactory {
    public static Notification createLikeNotification(Video video, User user) {
        return Notification.builder()
                .video(video)
                .user(user)
                .notificationType(NotificationType.LIKE)
                .isRead(false)
                .build();
    }

    public static Notification createDisLikeNotification(Video video, User user) {
        return Notification.builder()
                .video(video)
                .user(user)
                .notificationType(NotificationType.DISLIKE)
                .isRead(false)
                .build();
    }
}
