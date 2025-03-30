package org.stefan.media_app.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.stefan.media_app.dtos.responses.NotificationResponseDto;
import org.stefan.media_app.models.Notification;

@Component
@RequiredArgsConstructor
public class NotificationMapper {
    private final VideoMapper videoMapper;

    public NotificationResponseDto mapToResponseDto(Notification notification) {
        if (notification == null) {
            return null;
        }

        return NotificationResponseDto.builder()
                .notificationType(notification.getNotificationType())
                .id(notification.getId())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .isRead(notification.isRead())
                .video(videoMapper.mapToLowInfoResponseDto(notification.getVideo()))
                .build();
    }
}
