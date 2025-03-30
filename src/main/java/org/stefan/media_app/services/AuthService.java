package org.stefan.media_app.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.stefan.media_app.dtos.requests.LoginRequestDto;
import org.stefan.media_app.dtos.requests.RegisterRequestDto;
import org.stefan.media_app.models.User;

public interface AuthService {
    void register(RegisterRequestDto registerRequestDto);

    void login(@Valid LoginRequestDto loginRequestDto, HttpServletRequest request);

    void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    User getCurrentlyAuthenticatedUser();
}
