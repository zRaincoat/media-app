package org.stefan.media_app.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequestDto {
    @Email(message = "must be a valid email")
    private String email;
    @NotBlank(message = "must not be blank")
    private String name;
    @NotBlank(message = "must not be blank")
    private String surname;
    @Pattern(regexp = "\\+?[0-9]{10,15}")
    private String phoneNumber;
    @NotBlank(message = "must not be blank")
    private String address;
    @NotNull(message = "must not be null")
    private Integer age;
}
