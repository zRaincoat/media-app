package org.stefan.bankapp.mappers;

import java.util.List;
import org.springframework.stereotype.Component;
import org.stefan.bankapp.dtos.requests.RegisterRequestDto;
import org.stefan.bankapp.dtos.responses.PlayListResponseDto;
import org.stefan.bankapp.dtos.responses.UserLowInfoResponseDto;
import org.stefan.bankapp.dtos.responses.UserResponseDto;
import org.stefan.bankapp.models.User;

import static org.stefan.bankapp.models.User.calculateAgeByBirthDate;

@Component
public class UserMapper {

    public User mapToModel(final RegisterRequestDto registerRequestDto) {
        if (registerRequestDto == null) {
            return null;
        }
        return User.builder()
                .name(registerRequestDto.getName())
                .surname(registerRequestDto.getSurname())
                .email(registerRequestDto.getEmail())
                .password(registerRequestDto.getPassword())
                .gender(registerRequestDto.getGender())
                .phoneNumber(registerRequestDto.getPhoneNumber())
                .birthDate(registerRequestDto.getBirthDate())
                .build();
    }

    public UserResponseDto mapToResponseDto(User user, List<PlayListResponseDto> playLists) {
        if (user == null) {
            return null;
        }
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .age(calculateAgeByBirthDate(user.getBirthDate()))
                .createdAt(user.getCreatedAt() == null ? null : user.getCreatedAt().toString())
                .updatedAt(user.getUpdatedAt() == null ? null : user.getUpdatedAt().toString())
                .playLists(playLists)
                .build();
    }

    public UserLowInfoResponseDto mapToLowInfoResponseDto(User user) {
        if (user == null) {
            return null;
        }
        return UserLowInfoResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();
    }


}
