package org.stefan.media_app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.stefan.media_app.dtos.responses.NotificationResponseDto;

public interface NotificationService {
    Page<NotificationResponseDto> getNotificationsByAuthUser(Pageable pageable);
}
