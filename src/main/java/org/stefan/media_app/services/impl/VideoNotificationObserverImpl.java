package org.stefan.media_app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.stefan.media_app.factories.NotificationFactory;
import org.stefan.media_app.models.Notification;
import org.stefan.media_app.models.User;
import org.stefan.media_app.models.Video;
import org.stefan.media_app.repositories.NotificationRepository;
import org.stefan.media_app.services.VideoNotificationObserver;

@Service
@RequiredArgsConstructor
public class VideoNotificationObserverImpl implements VideoNotificationObserver {
    private final NotificationRepository notificationRepository;

    @Override
    public void onVideoLiked(Video video, User user) {
        Notification notification = NotificationFactory.createLikeNotification(video, user);
        notificationRepository.save(notification);
    }

    @Override
    public void onVideoDisliked(Video video, User user) {
        Notification notification = NotificationFactory.createDisLikeNotification(video, user);
        notificationRepository.save(notification);
    }
}
