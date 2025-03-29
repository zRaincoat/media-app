package org.stefan.media_app.services.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.stefan.media_app.dtos.responses.VideoResponseDto;
import org.stefan.media_app.mappers.VideoMapper;
import org.stefan.media_app.models.Video;
import org.stefan.media_app.repositories.VideoRepository;
import org.stefan.media_app.services.PlayListVideoService;

@Service
@RequiredArgsConstructor
public class PlayListVideoServiceImpl implements PlayListVideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    @Override
    public Map<UUID, List<Video>> getPlayListIdsToVideosByUserId(UUID userId) {
        return videoRepository.getVideosWithPlayListsByUserId(userId).stream()
                .collect(Collectors.groupingBy(video -> video.getPlayList().getId()));
    }

    @Override
    public List<VideoResponseDto> getVideosInfoByPlayListId(UUID playListId) {
        return videoRepository.findByPlayListId(playListId).stream()
                .map(videoMapper::mapToResponseDto)
                .toList();
    }
}