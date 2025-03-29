package org.stefan.media_app.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import org.stefan.media_app.enums.Category;

@Data
public class VideoRequestDto {
    @NotNull(message = "must not be null")
    private UUID playListId;
    @NotBlank(message = "must not be blank")
    private String title;
    private String description;
    private String url;
    private Set<Category> categories;
}
