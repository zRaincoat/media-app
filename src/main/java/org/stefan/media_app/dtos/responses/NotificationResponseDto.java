package org.stefan.media_app.dtos.responses;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.stefan.media_app.enums.NotificationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDto {
    private UUID id;
    private NotificationType notificationType;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private VideoLowInfoResponseDto video;
}
