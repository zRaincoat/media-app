package org.stefan.bankapp.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.stefan.bankapp.dtos.requests.LoginRequestDto;
import org.stefan.bankapp.dtos.requests.RegisterRequestDto;
import org.stefan.bankapp.models.User;

public interface AuthService {
    void register(RegisterRequestDto registerRequestDto);

    void login(@Valid LoginRequestDto loginRequestDto);

    void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    User getCurrentlyAuthentificatedUser();
}
