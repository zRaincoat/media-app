package org.stefan.mediaApp.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.stefan.mediaApp.dtos.responses.VideoResponseDto;
import org.stefan.mediaApp.models.Video;

public interface PlayListVideoService {
    Map<UUID, List<Video>> getPlayListIdsToVideosByUserId(UUID userId);

    List<VideoResponseDto> getVideosInfoByPlayListId(UUID playListId);
}
