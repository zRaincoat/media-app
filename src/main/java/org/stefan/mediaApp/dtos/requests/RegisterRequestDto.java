package org.stefan.mediaApp.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Data;
import org.stefan.mediaApp.enums.Gender;

@Data
public class RegisterRequestDto {

    @NotBlank(message = "must not be blank")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "must not be blank")
    @Size(min = 2, max = 50)
    private String surname;

    @NotBlank(message = "must not be blank")
    @Size(min = 4)
    private String password;

    @NotBlank(message = "must not be blank")
    @Email(message = "must be a valid email")
    private String email;

    @NotBlank(message = "must not be blank")
    @Pattern(regexp = "\\+?[0-9]{10,15}")
    private String phoneNumber;

    @NotNull(message = "must not be null")
    private Gender gender;

    @NotNull(message = "must not be null")
    @Past(message = "must be in the past")
    private LocalDate birthDate;
}
