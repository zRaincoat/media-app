package org.stefan.media_app.services;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.stefan.media_app.dtos.requests.VideoRequestDto;
import org.stefan.media_app.dtos.requests.VideoUpdateRequestDto;
import org.stefan.media_app.dtos.responses.VideoFullInfoResponseDto;
import org.stefan.media_app.dtos.responses.VideoResponseDto;
import org.stefan.media_app.enums.VideoSortBy;
import org.stefan.media_app.models.User;
import org.stefan.media_app.models.Video;

public interface VideoService {
    void addVideo(VideoRequestDto videoRequestDto);

    void updateVideo(UUID id, VideoUpdateRequestDto videoRequestDto);

    Video findByIdAndUser(UUID videoId, User user);

    void deleteVideo(UUID id);

    void likeVideo(UUID id);

    Video getVideoById(UUID id);

    void dislikeVideo(UUID id);

    VideoFullInfoResponseDto getVideo(UUID id);

    Page<VideoResponseDto> getAllVideos(VideoSortBy sort, Pageable pageable);
}
