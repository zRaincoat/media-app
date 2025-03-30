package org.stefan.media_app;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.stefan.media_app.dtos.requests.VideoRequestDto;
import org.stefan.media_app.dtos.requests.VideoUpdateRequestDto;
import org.stefan.media_app.dtos.responses.VideoResponseDto;
import org.stefan.media_app.enums.VideoSortBy;
import org.stefan.media_app.mappers.VideoMapper;
import org.stefan.media_app.models.PlayList;
import org.stefan.media_app.models.User;
import org.stefan.media_app.models.Video;
import org.stefan.media_app.repositories.VideoRepository;
import org.stefan.media_app.services.AuthService;
import org.stefan.media_app.services.PlayListService;
import org.stefan.media_app.services.impl.VideoEventSubject;
import org.stefan.media_app.services.impl.VideoServiceImpl;
import org.stefan.media_app.services.impl.VideoSortContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VideoServiceTest {

    public static final String NEW_TITLE = "New Title";
    public static final String NEW_DESCRIPTION = "New Description";
    public static final String OLD_TITLE = "Old Title";
    public static final String OLD_DESCRIPTION = "Old Description";
    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoMapper videoMapper;

    @Mock
    private PlayListService playListService;

    @Mock
    private AuthService authService;

    @Mock
    private VideoEventSubject videoEventSubject;

    @Mock
    private VideoSortContext videoSortContext;

    private VideoServiceImpl videoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        videoService = new VideoServiceImpl(videoRepository, videoMapper, playListService, authService, videoEventSubject, videoSortContext);
    }

    @Test
    void addVideo_savesVideo() {
        VideoRequestDto videoRequestDto = new VideoRequestDto();
        User user = new User();
        PlayList playList = new PlayList();
        Video video = new Video();

        when(authService.getCurrentlyAuthenticatedUser()).thenReturn(user);
        when(playListService.findByIdAndUser(videoRequestDto.getPlayListId(), user)).thenReturn(playList);
        when(videoMapper.mapToModel(videoRequestDto, playList)).thenReturn(video);

        videoService.addVideo(videoRequestDto);

        verify(videoRepository).save(video);
    }

    @Test
    void updateVideo_updatesVideoDetails() {
        UUID id = UUID.randomUUID();
        VideoUpdateRequestDto videoUpdateRequestDto = new VideoUpdateRequestDto();
        videoUpdateRequestDto.setTitle(NEW_TITLE);
        videoUpdateRequestDto.setDescription(NEW_DESCRIPTION);

        User user = new User();
        Video video = new Video();
        video.setTitle(OLD_TITLE);
        video.setDescription(OLD_DESCRIPTION);

        when(authService.getCurrentlyAuthenticatedUser()).thenReturn(user);
        when(videoRepository.findByIdAndUser(id, user)).thenReturn(Optional.of(video));

        videoService.updateVideo(id, videoUpdateRequestDto);

        assertEquals(NEW_TITLE, video.getTitle());
        assertEquals(NEW_DESCRIPTION, video.getDescription());
    }

    @Test
    void deleteVideo_softDeletesVideo() {
        UUID id = UUID.randomUUID();
        User user = new User();
        Video video = new Video();

        when(authService.getCurrentlyAuthenticatedUser()).thenReturn(user);
        when(videoRepository.findByIdAndUser(id, user)).thenReturn(Optional.of(video));

        videoService.deleteVideo(id);

        assertNotNull(video.getDeletedAt());
    }

    @Test
    void likeVideo_notifiesObservers() {
        UUID id = UUID.randomUUID();
        User user = new User();
        Video video = new Video();

        when(authService.getCurrentlyAuthenticatedUser()).thenReturn(user);
        when(videoRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(video));

        videoService.likeVideo(id);

        verify(videoEventSubject).notifyVideoLiked(video, user);
    }

    @Test
    void dislikeVideo_notifiesObservers() {
        UUID id = UUID.randomUUID();
        User user = new User();
        Video video = new Video();

        when(authService.getCurrentlyAuthenticatedUser()).thenReturn(user);
        when(videoRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(video));

        videoService.dislikeVideo(id);

        verify(videoEventSubject).notifyVideoDisliked(video, user);
    }

    @Test
    void getVideo_returnsVideoFullInfoResponseDto() {
        UUID id = UUID.randomUUID();
        Video video = new Video();
        PlayList playList = new PlayList();
        playList.setId(UUID.randomUUID());
        video.setPlayList(playList);

        int playListVideoCount = 5;

        when(videoRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(video));
        when(videoRepository.getPlayListVideoCount(video.getPlayList().getId())).thenReturn(playListVideoCount);

        videoService.getVideo(id);

        verify(videoMapper).mapToFullInfoResponseDto(video, playListVideoCount);
    }

    @Test
    void addVideo_whenPlayListNotFound_throwsEntityNotFoundException() {
        VideoRequestDto videoRequestDto = new VideoRequestDto();
        videoRequestDto.setPlayListId(UUID.randomUUID());
        User user = new User();

        when(authService.getCurrentlyAuthenticatedUser()).thenReturn(user);
        when(playListService.findByIdAndUser(videoRequestDto.getPlayListId(), user))
                .thenThrow(new EntityNotFoundException("PlayList not found"));

        assertThrows(EntityNotFoundException.class, () -> videoService.addVideo(videoRequestDto));
    }


    @Test
    void updateVideo_whenVideoNotFound_throwsEntityNotFoundException() {
        UUID id = UUID.randomUUID();
        VideoUpdateRequestDto videoUpdateRequestDto = new VideoUpdateRequestDto();
        User user = new User();

        when(authService.getCurrentlyAuthenticatedUser()).thenReturn(user);
        when(videoRepository.findByIdAndUser(id, user)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoService.updateVideo(id, videoUpdateRequestDto));
    }

    @Test
    void deleteVideo_whenVideoNotFound_throwsEntityNotFoundException() {
        UUID id = UUID.randomUUID();
        User user = new User();

        when(authService.getCurrentlyAuthenticatedUser()).thenReturn(user);
        when(videoRepository.findByIdAndUser(id, user)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoService.deleteVideo(id));
    }

    @Test
    void likeVideo_whenVideoNotFound_throwsEntityNotFoundException() {
        UUID id = UUID.randomUUID();
        User user = new User();

        when(authService.getCurrentlyAuthenticatedUser()).thenReturn(user);
        when(videoRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoService.likeVideo(id));
    }

    @Test
    void dislikeVideo_whenVideoNotFound_throwsEntityNotFoundException() {
        UUID id = UUID.randomUUID();
        User user = new User();

        when(authService.getCurrentlyAuthenticatedUser()).thenReturn(user);
        when(videoRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoService.dislikeVideo(id));
    }

    @Test
    void getVideo_whenVideoNotFound_throwsEntityNotFoundException() {
        UUID id = UUID.randomUUID();

        when(videoRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoService.getVideo(id));
    }

    @Test
    void findByIdAndUser_whenVideoNotFound_throwsEntityNotFoundException() {
        UUID id = UUID.randomUUID();
        User user = new User();

        when(videoRepository.findByIdAndUser(id, user)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> videoService.findByIdAndUser(id, user));
    }

    @Test
    void shouldReturnPagedVideosSortedByLikesAsc() {
        List<Video> mockVideos = List.of(new Video(), new Video());
        Pageable pageable = PageRequest.of(0, 2);

        when(videoSortContext.getSortedVideos(VideoSortBy.LIKES_ASC)).thenReturn(mockVideos);
        when(videoMapper.mapToResponseDto(any())).thenReturn(new VideoResponseDto());

        Page<VideoResponseDto> result = videoService.getAllVideos(VideoSortBy.LIKES_ASC, pageable);

        assertEquals(2, result.getContent().size());
        verify(videoSortContext).getSortedVideos(VideoSortBy.LIKES_ASC);
    }

    @Test
    void shouldReturnPagedVideosSortedByUpdatedAtDesc() {
        List<Video> mockVideos = List.of(new Video());
        Pageable pageable = PageRequest.of(0, 1);

        when(videoSortContext.getSortedVideos(VideoSortBy.UPDATED_AT_DESC)).thenReturn(mockVideos);
        when(videoMapper.mapToResponseDto(any())).thenReturn(new VideoResponseDto());

        Page<VideoResponseDto> result = videoService.getAllVideos(VideoSortBy.UPDATED_AT_DESC, pageable);

        assertEquals(1, result.getContent().size());
        verify(videoSortContext).getSortedVideos(VideoSortBy.UPDATED_AT_DESC);
    }
}
