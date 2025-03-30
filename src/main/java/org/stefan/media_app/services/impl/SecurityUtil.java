package org.stefan.media_app.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.stefan.media_app.models.User;
import org.stefan.media_app.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("No authenticated user found");
        }
        Object principal = authentication.getPrincipal();
        String email = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        return userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));
    }
}