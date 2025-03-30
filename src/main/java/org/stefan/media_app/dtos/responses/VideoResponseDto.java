package org.stefan.media_app.dtos.responses;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.stefan.media_app.enums.Category;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
