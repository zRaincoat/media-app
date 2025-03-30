package org.stefan.media_app;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.stefan.media_app.dtos.responses.NotificationResponseDto;
import org.stefan.media_app.mappers.NotificationMapper;
import org.stefan.media_app.models.Notification;
import org.stefan.media_app.models.User;
import org.stefan.media_app.repositories.NotificationRepository;
import org.stefan.media_app.services.AuthService;
import org.stefan.media_app.services.impl.NotificationServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private AuthService authService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @Mock
    private Pageable pageable;

    @Test
    void getNotificationsByAuthUser_returnsMappedPage() {
        User user = new User();
        Notification notification1 = new Notification();
        Notification notification2 = new Notification();
        List<Notification> notifications = List.of(notification1, notification2);
        Page<Notification> notificationsPage = new PageImpl<>(notifications);
        NotificationResponseDto dto1 = new NotificationResponseDto();
        NotificationResponseDto dto2 = new NotificationResponseDto();

        when(authService.getCurrentlyAuthenticatedUser()).thenReturn(user);
        when(notificationRepository.getAllNotificationsByUser(user, pageable)).thenReturn(notificationsPage);

        when(notificationMapper.mapToResponseDto(notification1)).thenReturn(dto1);
        when(notificationMapper.mapToResponseDto(notification2)).thenReturn(dto2);

        Page<NotificationResponseDto> result = notificationService.getNotificationsByAuthUser(pageable);

        assertNotNull(result);
        List<NotificationResponseDto> content = result.getContent();
        assertEquals(2, content.size());
        assertTrue(content.containsAll(List.of(dto1, dto2)));

        verify(authService).getCurrentlyAuthenticatedUser();
        verify(notificationRepository).getAllNotificationsByUser(user, pageable);
        verify(notificationRepository).fetchAllWithVideos(notifications);
        ArgumentCaptor<List<UUID>> captor = ArgumentCaptor.forClass(List.class);
        verify(notificationRepository).setAllNotificationsIsReadTrue(captor.capture());

        assertEquals(2, captor.getValue().size());
    }

    @Test
    void getNotificationsByAuthUser_emptyPage_returnsEmptyPage() {
        User user = new User();
        Page<Notification> emptyPage = new PageImpl<>(Collections.emptyList());

        when(authService.getCurrentlyAuthenticatedUser()).thenReturn(user);
        when(notificationRepository.getAllNotificationsByUser(user, pageable)).thenReturn(emptyPage);

        Page<NotificationResponseDto> result = notificationService.getNotificationsByAuthUser(pageable);
        assertTrue(result.getContent().isEmpty());

        verify(authService).getCurrentlyAuthenticatedUser();
        verify(notificationRepository).getAllNotificationsByUser(user, pageable);
        verify(notificationRepository).fetchAllWithVideos(Collections.emptyList());
        verify(notificationRepository).setAllNotificationsIsReadTrue(Collections.emptyList());
    }
}