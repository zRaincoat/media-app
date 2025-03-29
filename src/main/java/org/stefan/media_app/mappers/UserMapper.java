package org.stefan.media_app.mappers;

import java.util.List;
import org.springframework.stereotype.Component;
import org.stefan.media_app.dtos.requests.RegisterRequestDto;
import org.stefan.media_app.dtos.responses.PlayListResponseDto;
import org.stefan.media_app.dtos.responses.UserLowInfoResponseDto;
import org.stefan.media_app.dtos.responses.UserResponseDto;
import org.stefan.media_app.dtos.responses.UserWithAllDataResponseDto;
import org.stefan.media_app.models.User;

import static org.stefan.media_app.models.User.calculateAgeByBirthDate;

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

    public UserWithAllDataResponseDto mapToWithAllDataResponseDto(User user, List<PlayListResponseDto> playLists) {
        if (user == null) {
            return null;
        }
        return UserWithAllDataResponseDto.builder()
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

    public UserResponseDto mapToResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .birthDate(user.getBirthDate() == null ? null : user.getBirthDate().toString())
                .age(calculateAgeByBirthDate(user.getBirthDate()))
                .createdAt(user.getCreatedAt() == null ? null : user.getCreatedAt().toString())
                .updatedAt(user.getUpdatedAt() == null ? null : user.getUpdatedAt().toString())
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
