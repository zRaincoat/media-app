package org.stefan.bankapp.mappers;

import org.springframework.stereotype.Component;
import org.stefan.bankapp.dtos.requests.RegisterRequestDto;
import org.stefan.bankapp.models.User;

@Component
public class UserMapper {

    public User mapToModel(final RegisterRequestDto registerRequestDto) {
        return User.builder()
                .age(registerRequestDto.getAge())
                .name(registerRequestDto.getName())
                .surname(registerRequestDto.getSurname())
                .email(registerRequestDto.getEmail())
                .birthDate(registerRequestDto.getBirthDate())
                .password(registerRequestDto.getPassword())
                .gender(registerRequestDto.getGender())
                .phoneNumber(registerRequestDto.getPhoneNumber())
                .build();
    }
}
