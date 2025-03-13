package org.stefan.bankapp.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import org.stefan.bankapp.dtos.requests.UserRequestDto;
import org.stefan.bankapp.dtos.responses.UserResponseDto;
import org.stefan.bankapp.models.User;

public interface UserService {

    boolean existsByEmail(String email);

    void save(User user);

    User getByEmailAndDeletedAtIsNull(String email);

    void updateRegisteredUser(UserRequestDto requestDto);

    void deleteAuthUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    UserResponseDto getUserById(UUID id);

    UserResponseDto getMe();
}
