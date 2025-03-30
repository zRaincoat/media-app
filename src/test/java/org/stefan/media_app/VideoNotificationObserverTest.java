package org.stefan.media_app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stefan.media_app.factories.NotificationFactory;
import org.stefan.media_app.models.Notification;
import org.stefan.media_app.models.User;
import org.stefan.media_app.models.Video;
import org.stefan.media_app.repositories.NotificationRepository;
import org.stefan.media_app.services.impl.VideoNotificationObserverImpl;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VideoNotificationObserverTest {

    @InjectMocks
    private VideoNotificationObserverImpl observer;

    @Mock
    private NotificationRepository notificationRepository;

    @Test
    void onVideoLiked_callsFactoryAndSavesNotification() {
        Video video = new Video();
        User user = new User();
        Notification likeNotification = new Notification();

        try (MockedStatic<NotificationFactory> factoryMock = mockStatic(NotificationFactory.class)) {
            factoryMock.when(() -> NotificationFactory.createLikeNotification(video, user))
                    .thenReturn(likeNotification);
            observer.onVideoLiked(video, user);
            factoryMock.verify(() -> NotificationFactory.createLikeNotification(video, user));
        }
        verify(notificationRepository).save(likeNotification);
    }

    @Test
    void onVideoDisliked_callsFactoryAndSavesNotification() {
        Video video = new Video();
        User user = new User();
        Notification dislikeNotification = new Notification();

        try (MockedStatic<NotificationFactory> factoryMock = mockStatic(NotificationFactory.class)) {
            factoryMock.when(() -> NotificationFactory.createDisLikeNotification(video, user))
                    .thenReturn(dislikeNotification);
            observer.onVideoDisliked(video, user);
            factoryMock.verify(() -> NotificationFactory.createDisLikeNotification(video, user));
        }
        verify(notificationRepository).save(dislikeNotification);
    }
}