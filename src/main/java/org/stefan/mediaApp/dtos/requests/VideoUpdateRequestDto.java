package org.stefan.mediaApp.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.Data;
import org.stefan.mediaApp.enums.Category;

@Data
public class VideoUpdateRequestDto {
    @NotBlank(message = "must not be blank")
    private String title;
    private String description;
    private String url;
    private Set<Category> categories;
}
