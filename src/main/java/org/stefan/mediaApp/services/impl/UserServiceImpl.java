package org.stefan.mediaApp.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stefan.mediaApp.dtos.requests.UserRequestDto;
import org.stefan.mediaApp.dtos.responses.PlayListResponseDto;
import org.stefan.mediaApp.dtos.responses.UserResponseDto;
import org.stefan.mediaApp.dtos.responses.UserWithAllDataResponseDto;
import org.stefan.mediaApp.mappers.UserMapper;
import org.stefan.mediaApp.models.User;
import org.stefan.mediaApp.repositories.UserRepository;
import org.stefan.mediaApp.services.AuthService;
import org.stefan.mediaApp.services.PlayListService;
import org.stefan.mediaApp.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final PlayListService playListService;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmailAndDeletedAtIsNull(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getByEmailAndDeletedAtIsNull(String email) {
        return userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email" + email + "not found"));
    }

    @Override
    @Transactional
    public void updateRegisteredUser(UserRequestDto requestDto) {
        User authUser = authService.getCurrentlyAuthentificatedUser();
        setFields(requestDto, authUser);
    }

    @Override
    @Transactional
    public void deleteAuthUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        User authUser = authService.getCurrentlyAuthentificatedUser();
        authService.logout(httpServletRequest, httpServletResponse);
        authUser.softDelete();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id" + id + "not found"));
        return userMapper.mapToResponseDto(user);
    }

    @Override
    public UserWithAllDataResponseDto getMe() {
        User user = authService.getCurrentlyAuthentificatedUser();
        List<PlayListResponseDto> playLists = playListService.getAllPlayListsInfoByUser(user.getId());
        return userMapper.mapToWithAllDataResponseDto(user, playLists);
    }

    private void setFields(UserRequestDto requestDto, User authUser) {
        authUser.setName(requestDto.getName());
        authUser.setSurname(requestDto.getSurname());
        authUser.setPhoneNumber(requestDto.getPhoneNumber());
    }
}
