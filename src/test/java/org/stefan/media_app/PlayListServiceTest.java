package org.stefan.media_app;

import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stefan.media_app.dtos.requests.PlayListRequestDto;
import org.stefan.media_app.dtos.responses.PlayListResponseDto;
import org.stefan.media_app.dtos.responses.VideoResponseDto;
import org.stefan.media_app.mappers.PlayListMapper;
import org.stefan.media_app.mappers.VideoMapper;
import org.stefan.media_app.models.PlayList;
import org.stefan.media_app.models.User;
import org.stefan.media_app.models.Video;
import org.stefan.media_app.repositories.PlayListRepository;
import org.stefan.media_app.services.impl.PlayListServiceImpl;
import org.stefan.media_app.services.impl.PlayListVideoServiceImpl;
import org.stefan.media_app.services.impl.SecurityUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayListServiceTest {

    public static final String NEW_TITLE = "New Title";
    public static final String OLD_TITLE = "Old Title";

    @InjectMocks
    private PlayListServiceImpl playListService;

    @Mock
    private PlayListRepository playListRepository;

    @Mock
    private PlayListMapper playListMapper;

    @Mock
    private VideoMapper videoMapper;

    @Mock
    private PlayListVideoServiceImpl playListVideoService;

    @Mock
    private SecurityUtil securityUtil;

    @Test
    void getAllPlayListsInfoByUser_returnsResponseDtosWithVideos() {
        UUID userId = UUID.randomUUID();
        PlayListResponseDto dto1 = new PlayListResponseDto();
        PlayListResponseDto dto2 = new PlayListResponseDto();
        dto1.setId(UUID.randomUUID());
        dto2.setId(UUID.randomUUID());

        when(playListRepository.getPlayListsInfoByUser(userId)).thenReturn(List.of(dto1, dto2));

        Video video1 = new Video();
        Video video2 = new Video();
        when(playListVideoService.getPlayListIdsToVideosByUserId(userId))
                .thenReturn(Map.of(dto1.getId(), List.of(video1), dto2.getId(), List.of(video2)));

        when(videoMapper.mapToResponseDto(video1)).thenReturn(new VideoResponseDto());
        when(videoMapper.mapToResponseDto(video2)).thenReturn(new VideoResponseDto());

        List<PlayListResponseDto> result = playListService.getAllPlayListsInfoByUser(userId);

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getVideos().size());
        assertEquals(1, result.get(1).getVideos().size());
    }

    @Test
    void getAllPlayListsInfoByUser_emptyList() {
        UUID userId = UUID.randomUUID();
        when(playListRepository.getPlayListsInfoByUser(userId)).thenReturn(Collections.emptyList());
        when(playListVideoService.getPlayListIdsToVideosByUserId(userId)).thenReturn(Collections.emptyMap());

        List<PlayListResponseDto> result = playListService.getAllPlayListsInfoByUser(userId);

        assertTrue(result.isEmpty());
    }

    @Test
    void getPlayListInfo_returnsDtoWithVideos() {
        UUID playListId = UUID.randomUUID();
        PlayListResponseDto dto = new PlayListResponseDto();
        dto.setId(playListId);

        when(playListRepository.getPlayListInfoById(playListId)).thenReturn(Optional.of(dto));
        when(playListVideoService.getVideosInfoByPlayListId(playListId)).thenReturn(List.of(new VideoResponseDto()));

        PlayListResponseDto result = playListService.getPlayListInfo(playListId);

        assertFalse(result.getVideos().isEmpty());
    }

    @Test
    void getPlayListInfo_notFound_throwsException() {
        UUID playListId = UUID.randomUUID();
        when(playListRepository.getPlayListInfoById(playListId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playListService.getPlayListInfo(playListId));
    }

    @Test
    void findByIdAndUser_returnsPlaylist() {
        UUID playListId = UUID.randomUUID();
        User user = new User();
        PlayList playList = new PlayList();

        when(playListRepository.findByIdAndUser(playListId, user)).thenReturn(Optional.of(playList));

        PlayList result = playListService.findByIdAndUser(playListId, user);

        assertEquals(playList, result);
    }

    @Test
    void findByIdAndUser_notFound_throwsException() {
        UUID playListId = UUID.randomUUID();
        User user = new User();

        when(playListRepository.findByIdAndUser(playListId, user)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playListService.findByIdAndUser(playListId, user));
    }

    @Test
    void addPlayList_successful() {
        PlayListRequestDto requestDto = new PlayListRequestDto();
        PlayList playList = new PlayList();
        User user = new User();

        when(playListMapper.mapToModel(requestDto, user)).thenReturn(playList);
        when(securityUtil.getCurrentUser()).thenReturn(user);

        playListService.addPlayList(requestDto);

        verify(playListRepository).save(playList);
    }

    @Test
    void addPlayList_securityUtilThrowsException_propagatesException() {
        PlayListRequestDto requestDto = new PlayListRequestDto();

        when(securityUtil.getCurrentUser()).thenThrow(new EntityNotFoundException("User not found"));

        assertThrows(EntityNotFoundException.class, () -> playListService.addPlayList(requestDto));
    }


    @Test
    void deletePlayList_successful() {
        UUID playListId = UUID.randomUUID();
        User user = new User();
        PlayList playList = spy(new PlayList());

        when(securityUtil.getCurrentUser()).thenReturn(user);
        when(playListRepository.findByIdAndUser(playListId, user)).thenReturn(Optional.of(playList));

        playListService.deletePlayList(playListId);

        verify(playList).softDelete();
    }

    @Test
    void deletePlayList_notFound_throwsException() {
        UUID playListId = UUID.randomUUID();

        User user = new User();
        when(securityUtil.getCurrentUser()).thenReturn(user);
        when(playListRepository.findByIdAndUser(playListId, user)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playListService.deletePlayList(playListId));
    }

    @Test
    void updatePlayList_successful() {
        UUID playListId = UUID.randomUUID();
        PlayListRequestDto requestDto = new PlayListRequestDto();
        requestDto.setTitle(NEW_TITLE);

        PlayList playList = spy(new PlayList());
        playList.setTitle(OLD_TITLE);

        User user = new User();
        when(playListRepository.findByIdAndUser(playListId, user)).thenReturn(Optional.of(playList));
        when(securityUtil.getCurrentUser()).thenReturn(user);

        playListService.updatePlayList(playListId, requestDto);

        assertEquals(NEW_TITLE, playList.getTitle());
    }

    @Test
    void updatePlayList_notFound_throwsException() {
        UUID playListId = UUID.randomUUID();
        PlayListRequestDto requestDto = new PlayListRequestDto();
        User user = new User();
        when(securityUtil.getCurrentUser()).thenReturn(user);
        when(playListRepository.findByIdAndUser(playListId, user)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playListService.updatePlayList(playListId, requestDto));
    }
}