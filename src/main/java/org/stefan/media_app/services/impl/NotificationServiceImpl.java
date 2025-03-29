package org.stefan.media_app.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stefan.media_app.dtos.responses.NotificationResponseDto;
import org.stefan.media_app.mappers.NotificationMapper;
import org.stefan.media_app.models.AbstractEntity;
import org.stefan.media_app.models.Notification;
import org.stefan.media_app.models.User;
import org.stefan.media_app.repositories.NotificationRepository;
import org.stefan.media_app.services.AuthService;
import org.stefan.media_app.services.NotificationService;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final AuthService authService;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    @Transactional
    public Page<NotificationResponseDto> getNotificationsByAuthUser(Pageable pageable) {
        User user = authService.getCurrentlyAuthentificatedUser();
        Page<Notification> notificationsPage = notificationRepository.getAllNotificationsByUser(user, pageable);
        notificationRepository.fetchAllWithVideos(notificationsPage.getContent());
        notificationRepository.setAllNotificationsIsReadTrue(notificationsPage.getContent().stream().map(AbstractEntity::getId).toList());
        return notificationsPage.map(notificationMapper::mapToResponseDto);
    }
}
