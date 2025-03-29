package org.stefan.mediaApp.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import org.stefan.mediaApp.dtos.requests.UserRequestDto;
import org.stefan.mediaApp.dtos.responses.UserResponseDto;
import org.stefan.mediaApp.dtos.responses.UserWithAllDataResponseDto;
import org.stefan.mediaApp.models.User;

public interface UserService {

    boolean existsByEmail(String email);

    void save(User user);

    User getByEmailAndDeletedAtIsNull(String email);

    void updateRegisteredUser(UserRequestDto requestDto);

    void deleteAuthUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    UserResponseDto getUserById(UUID id);

    UserWithAllDataResponseDto getMe();
}
