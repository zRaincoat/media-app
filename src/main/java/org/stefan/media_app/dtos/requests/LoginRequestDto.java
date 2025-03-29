package org.stefan.media_app.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @Email(message = "must be a valid email")
    private String email;

    @NotBlank(message = "must not be blank")
    private String password;
}
