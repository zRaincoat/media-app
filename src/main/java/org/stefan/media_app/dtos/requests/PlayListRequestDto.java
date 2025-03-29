package org.stefan.media_app.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlayListRequestDto {
    @NotBlank(message = "must not be blank")
    private String title;
}
