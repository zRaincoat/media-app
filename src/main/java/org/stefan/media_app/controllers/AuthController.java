package org.stefan.media_app.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.stefan.media_app.dtos.requests.LoginRequestDto;
import org.stefan.media_app.dtos.requests.RegisterRequestDto;
import org.stefan.media_app.services.AuthService;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@Valid @RequestBody final RegisterRequestDto registerRequestDto) {
        authService.register(registerRequestDto);
        log.info("User with email: {} has been successfully registered", registerRequestDto.getEmail());
        return "Registration successful. Please, login.";
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody final LoginRequestDto loginRequestDto,
                        HttpServletRequest request) {
        authService.login(loginRequestDto, request);
        log.info("User with email: {} has been logged in", loginRequestDto.getEmail());
        return "Login successful.";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        authService.logout(httpServletRequest, httpServletResponse);
        log.info("User with email: {} has been logged out", httpServletRequest.getRemoteUser());
        return "Logout successful.";
    }
}
