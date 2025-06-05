package org.stefan.media_app.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import org.stefan.media_app.dtos.requests.UserRequestDto;
import org.stefan.media_app.dtos.responses.UserResponseDto;
import org.stefan.media_app.models.User;

public interface UserService {

    boolean existsByEmail(String email);

    void save(User user);

    User getByEmailAndDeletedAtIsNull(String email);

    void updateRegisteredUser(UserRequestDto requestDto);

    void deleteAuthUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    UserResponseDto getUserById(UUID id);

    UserResponseDto getMe();
}
