package org.stefan.media_app.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stefan.media_app.dtos.responses.NotificationResponseDto;
import org.stefan.media_app.services.NotificationService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public Page<NotificationResponseDto> getNotificationsByAuthUser(Pageable pageable) {
        log.info("Entering GET /notifications endpoint");
        return notificationService.getNotificationsByAuthUser(pageable);
    }
}
