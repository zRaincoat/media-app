package org.stefan.mediaApp.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.stefan.mediaApp.dtos.requests.LoginRequestDto;
import org.stefan.mediaApp.dtos.requests.RegisterRequestDto;
import org.stefan.mediaApp.models.User;

public interface AuthService {
    void register(RegisterRequestDto registerRequestDto);

    void login(@Valid LoginRequestDto loginRequestDto, HttpServletRequest request);

    void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    User getCurrentlyAuthentificatedUser();
}
