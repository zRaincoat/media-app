package org.stefan.media_app.dtos.responses;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoLowInfoResponseDto {
    private UUID id;
    private String title;
    private String url;
}
