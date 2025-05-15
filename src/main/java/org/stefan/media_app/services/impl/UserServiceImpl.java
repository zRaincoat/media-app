package org.stefan.media_app.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stefan.media_app.dtos.requests.UserRequestDto;
import org.stefan.media_app.dtos.responses.UserResponseDto;
import org.stefan.media_app.mappers.UserMapper;
import org.stefan.media_app.models.User;
import org.stefan.media_app.repositories.UserRepository;
import org.stefan.media_app.services.PlayListService;
import org.stefan.media_app.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PlayListService playListService;
    private final SecurityUtil securityUtil;
    private final LogoutUtil logoutUtil;

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
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));
    }

    @Override
    @Transactional
    public void updateRegisteredUser(UserRequestDto requestDto) {
        User authUser = securityUtil.getCurrentUser();
        setFields(requestDto, authUser);
    }

    @Override
    @Transactional
    public void deleteAuthUser(HttpServletRequest request, HttpServletResponse response) {
        logoutUtil.logout(request, response);
        User authUser = securityUtil.getCurrentUser();
        authUser.softDelete();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        return userMapper.mapToResponseDto(user);
    }

    @Override
    public UserResponseDto getMe() {
        User user = securityUtil.getCurrentUser();
        return userMapper.mapToResponseDto(user);
    }

    private void setFields(UserRequestDto requestDto, User authUser) {
        authUser.setName(requestDto.getName());
        authUser.setSurname(requestDto.getSurname());
        authUser.setPhoneNumber(requestDto.getPhoneNumber());
    }
}