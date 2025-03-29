package org.stefan.mediaApp.services.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.stefan.mediaApp.dtos.responses.VideoResponseDto;
import org.stefan.mediaApp.mappers.VideoMapper;
import org.stefan.mediaApp.models.Video;
import org.stefan.mediaApp.repositories.VideoRepository;
import org.stefan.mediaApp.services.PlayListVideoService;

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