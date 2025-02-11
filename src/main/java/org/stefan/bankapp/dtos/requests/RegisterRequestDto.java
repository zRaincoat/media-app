package org.stefan.bankapp.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Data;
import org.stefan.bankapp.enums.Gender;

@Data
public class RegisterRequestDto {

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(min = 2, max = 50)
    private String surname;

    @NotBlank
    @Size(min = 4)
    private String password;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "\\+?[0-9]{10,15}")
    private String phoneNumber;

    @Min(16)
    private int age;

    @NotNull
    private Gender gender;

    @NotNull
    @Past
    private LocalDate birthDate;
}
