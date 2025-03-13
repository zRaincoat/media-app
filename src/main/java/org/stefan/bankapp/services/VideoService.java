package org.stefan.bankapp.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.stefan.bankapp.dtos.requests.VideoRequestDto;
import org.stefan.bankapp.dtos.requests.VideoUpdateRequestDto;
import org.stefan.bankapp.dtos.responses.VideoFullInfoResponseDto;
import org.stefan.bankapp.dtos.responses.VideoResponseDto;
import org.stefan.bankapp.models.User;
import org.stefan.bankapp.models.Video;

public interface VideoService {
    void addVideo(VideoRequestDto videoRequestDto);

    void updateVideo(UUID id, VideoUpdateRequestDto videoRequestDto);

    Video findByIdAndUser(UUID videoId, User user);

    void deleteVideo(UUID id);

    void likeVideo(UUID id);

    Video getVideoById(UUID id);

    void dislikeVideo(UUID id);

    VideoFullInfoResponseDto getVideo(UUID id);

    List<VideoResponseDto> getVideosInfoByPlayListId(UUID playListId);

    List<VideoResponseDto> getVideosInfoByUserId(UUID userId);

    Map<UUID, List<Video>> getPlayListIdsToVideosByUserId(UUID userId);
}
