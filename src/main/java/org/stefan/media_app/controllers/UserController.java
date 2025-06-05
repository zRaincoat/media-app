package org.stefan.media_app.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.stefan.media_app.dtos.requests.UserRequestDto;
import org.stefan.media_app.dtos.responses.PlayListLowInfoResponseDto;
import org.stefan.media_app.dtos.responses.UserResponseDto;
import org.stefan.media_app.services.PlayListService;
import org.stefan.media_app.services.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PlayListService playListService;

    @PutMapping
    public void updateUser(@Valid @RequestBody UserRequestDto requestDto) {
        log.info("Entering PUT /users");
        userService.updateRegisteredUser(requestDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.info("Entering DELETE /users");
        userService.deleteAuthUser(httpServletRequest, httpServletResponse);
    }

    @Deprecated(forRemoval = true)
    @GetMapping("/me")
    public UserResponseDto getAuthUser() {
        log.info("Entering GET /users/me");
        return userService.getMe();
    }

    @GetMapping("/me/play-lists")
    public Page<PlayListLowInfoResponseDto> getPlayListsByAuthUser(Pageable pageable) {
        log.info("Entering GET /users/me/play-lists");
        return playListService.getPlayListsByAuthUser(pageable);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable UUID id) {
        log.info("Entering GET /users/{}", id);
        return userService.getUserById(id);
    }
}
