package org.stefan.media_app.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.Data;
import org.stefan.media_app.enums.Category;

@Data
public class VideoUpdateRequestDto {
    @NotBlank(message = "must not be blank")
    private String title;
    private String description;
    private String url;
    private Set<Category> categories;
}
