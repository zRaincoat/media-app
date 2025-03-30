package org.stefan.media_app.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stefan.media_app.dtos.requests.LoginRequestDto;
import org.stefan.media_app.dtos.requests.RegisterRequestDto;
import org.stefan.media_app.exceptions.AlreadyExistsException;
import org.stefan.media_app.mappers.UserMapper;
import org.stefan.media_app.models.User;
import org.stefan.media_app.services.AuthService;
import org.stefan.media_app.services.UserService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityUtil securityUtil;
    private final LogoutUtil logoutUtil;

    @Transactional
    @Override
    public void register(RegisterRequestDto registerRequestDto) {
        if (userService.existsByEmail(registerRequestDto.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }
        User user = userMapper.mapToModel(registerRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
    }

    @Override
    public void login(LoginRequestDto loginRequestDto, HttpServletRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        logoutUtil.logout(request, response);
    }

    @Override
    public User getCurrentlyAuthenticatedUser() {
        return securityUtil.getCurrentUser();
    }
}