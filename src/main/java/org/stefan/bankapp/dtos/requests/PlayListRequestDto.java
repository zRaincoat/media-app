package org.stefan.bankapp.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlayListRequestDto {
    @NotBlank(message = "must not be blank")
    private String title;
}
