package org.stefan.bankapp.dtos.responses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayListResponseDto {
    private UUID id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int videoCount;
    private List<VideoResponseDto> videos;
}
