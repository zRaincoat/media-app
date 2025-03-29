package org.stefan.mediaApp.dtos.responses;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.stefan.mediaApp.enums.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private UUID id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private String createdAt;
    private String updatedAt;
    private String birthDate;
    private int age;
}
