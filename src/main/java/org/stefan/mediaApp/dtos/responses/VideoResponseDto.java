package org.stefan.mediaApp.dtos.responses;

import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.stefan.mediaApp.enums.Category;

@Data
@Builder
public class VideoResponseDto {
    private UUID id;
    private String title;
    private String url;
    private UserLowInfoResponseDto author;
    private Set<Category> categories;
    private Long likes;
    private Long dislikes;
    private String createdAt;
    private String updatedAt;
}
