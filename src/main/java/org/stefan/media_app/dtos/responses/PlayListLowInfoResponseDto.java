package org.stefan.media_app.dtos.responses;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayListLowInfoResponseDto {
    private UUID id;
    private String title;
    private String createdAt;
    private String updatedAt;
    private int videoCount;
}
