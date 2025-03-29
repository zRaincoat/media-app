package org.stefan.media_app.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.stefan.media_app.dtos.responses.VideoResponseDto;
import org.stefan.media_app.models.Video;

public interface PlayListVideoService {
    Map<UUID, List<Video>> getPlayListIdsToVideosByUserId(UUID userId);

    List<VideoResponseDto> getVideosInfoByPlayListId(UUID playListId);
}
