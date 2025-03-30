package org.stefan.media_app;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.stefan.media_app.dtos.requests.LoginRequestDto;
import org.stefan.media_app.dtos.requests.RegisterRequestDto;
import org.stefan.media_app.exceptions.AlreadyExistsException;
import org.stefan.media_app.mappers.UserMapper;
import org.stefan.media_app.models.User;
import org.stefan.media_app.services.UserService;
import org.stefan.media_app.services.impl.AuthServiceImpl;
import org.stefan.media_app.services.impl.LogoutUtil;
import org.stefan.media_app.services.impl.SecurityUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    public static final String MAIL = "test@example.com";
    public static final String PASSWORD = "password";
    public static final String ENCODED_PASSWORD = "encodedPassword";
    public static final String WRONG_PASSWORD = "wrongPassword";
    public static final String BAD_CREDENTIALS = "Bad credentials";

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private LogoutUtil logoutUtil;

    @BeforeEach
    void setup() {
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void register_successful() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setEmail(MAIL);
        registerRequestDto.setPassword(PASSWORD);
        when(userService.existsByEmail(registerRequestDto.getEmail())).thenReturn(false);
        User user = new User();
        user.setEmail(MAIL);
        user.setPassword(PASSWORD);
        when(userMapper.mapToModel(registerRequestDto)).thenReturn(user);
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        authService.register(registerRequestDto);
        assertEquals(ENCODED_PASSWORD, user.getPassword());
        verify(userService).save(user);
    }

    @Test
    void register_emailAlreadyExists_throwsException() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setEmail(MAIL);
        when(userService.existsByEmail(registerRequestDto.getEmail())).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> authService.register(registerRequestDto));
        verify(userService, never()).save(any());
    }

    @Test
    void login_successful() {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(MAIL);
        loginRequestDto.setPassword(PASSWORD);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(request.getSession(true)).thenReturn(session);
        authService.login(loginRequestDto, request);
        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
        verify(request).getSession(true);
        verify(session).setAttribute(eq(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY), any());
    }

    @Test
    void login_authenticationFailure_throwsException() {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(MAIL);
        loginRequestDto.setPassword(WRONG_PASSWORD);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException(BAD_CREDENTIALS));
        assertThrows(BadCredentialsException.class, () -> authService.login(loginRequestDto, request));
    }

    @Test
    void logout_successful() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        authService.logout(request, response);
        verify(logoutUtil).logout(request, response);
    }

    @Test
    void getCurrentlyAuthUser_successful() {
        User expectedUser = new User();
        expectedUser.setEmail(MAIL);
        when(securityUtil.getCurrentUser()).thenReturn(expectedUser);
        User result = authService.getCurrentlyAuthenticatedUser();
        assertEquals(expectedUser, result);
    }

    @Test
    void getCurrentlyAuthUser_noAuthentication_throwsException() {
        when(securityUtil.getCurrentUser())
                .thenThrow(new AuthenticationCredentialsNotFoundException("No authenticated user found"));
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> authService.getCurrentlyAuthenticatedUser());
    }

    @Test
    void getCurrentlyAuthUser_notAuthenticated_throwsException() {
        when(securityUtil.getCurrentUser())
                .thenThrow(new AuthenticationCredentialsNotFoundException("Not authenticated"));
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> authService.getCurrentlyAuthenticatedUser());
    }

    @Test
    void additional_register_invokesUserMapperAndPasswordEncoder() {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setEmail(MAIL);
        dto.setPassword(PASSWORD);
        when(userService.existsByEmail(MAIL)).thenReturn(false);

        User user = new User();
        user.setEmail(MAIL);
        user.setPassword(PASSWORD);
        when(userMapper.mapToModel(dto)).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);

        authService.register(dto);

        verify(userMapper).mapToModel(dto);
        verify(passwordEncoder).encode(PASSWORD);
    }

    @Test
    void additional_login_setsSecurityContextCorrectly() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail(MAIL);
        dto.setPassword(PASSWORD);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(request.getSession(true)).thenReturn(session);
        authService.login(dto, request);
        assertEquals(auth, SecurityContextHolder.getContext().getAuthentication());
    }
}
