package org.stefan.mediaApp.services.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stefan.mediaApp.dtos.requests.PlayListRequestDto;
import org.stefan.mediaApp.dtos.responses.PlayListResponseDto;
import org.stefan.mediaApp.dtos.responses.VideoResponseDto;
import org.stefan.mediaApp.mappers.PlayListMapper;
import org.stefan.mediaApp.mappers.VideoMapper;
import org.stefan.mediaApp.models.AbstractEntity;
import org.stefan.mediaApp.models.PlayList;
import org.stefan.mediaApp.models.User;
import org.stefan.mediaApp.models.Video;
import org.stefan.mediaApp.repositories.PlayListRepository;
import org.stefan.mediaApp.services.AuthService;
import org.stefan.mediaApp.services.PlayListService;

@Service
@RequiredArgsConstructor
public class PlayListServiceImpl implements PlayListService {

    private final PlayListRepository playListRepository;
    private final AuthService authService;
    private final PlayListMapper playListMapper;
    private final VideoMapper videoMapper;
    private final PlayListVideoServiceImpl playListVideoService;

    @Override
    @Transactional(readOnly = true)
    public List<PlayListResponseDto> getAllPlayListsInfoByUser(UUID userId) {
        List<PlayListResponseDto> responseDtos = playListRepository.getPlayListsInfoByUser(userId);
        Map<UUID, List<Video>> videoMap = playListVideoService.getPlayListIdsToVideosByUserId(userId);
        responseDtos.forEach(playListResponseDto -> {
            List<VideoResponseDto> videoResponseDtos = videoMap.getOrDefault(playListResponseDto.getId(), new ArrayList<>())
                    .stream()
                    .map(videoMapper::mapToResponseDto)
                    .toList();
            playListResponseDto.setVideos(videoResponseDtos);
        });
        return responseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public PlayListResponseDto getPlayListInfo(UUID playListId) {
        PlayListResponseDto responseDto = playListRepository.getPlayListInfoById(playListId)
                .orElseThrow(() -> new EntityNotFoundException("PlayList with id " + playListId + " not found"));
        List<VideoResponseDto> videoResponseDtos = playListVideoService.getVideosInfoByPlayListId(playListId);
        responseDto.setVideos(videoResponseDtos);
        return responseDto;
    }

    @Override
    public PlayList findByIdAndUser(UUID playListId, User user) {
        return playListRepository.findByIdAndUser(playListId, user)
                .orElseThrow(() -> new EntityNotFoundException("PlayList with id " + playListId + " not found"));
    }

    @Override
    @Transactional
    public void addPlayList(PlayListRequestDto playListRequestDto) {
        User user = authService.getCurrentlyAuthentificatedUser();
        PlayList playList = playListMapper.mapToModel(playListRequestDto, user);
        playListRepository.save(playList);
    }

    @Override
    @Transactional
    public void deletePlayList(UUID id) {
        User user = authService.getCurrentlyAuthentificatedUser();
        PlayList playList = findByIdAndUser(id, user);
        playList.softDelete();
        playList.getVideoList().forEach(AbstractEntity::softDelete);
    }

    @Override
    @Transactional
    public void updatePlayList(UUID id, PlayListRequestDto playListRequestDto) {
        User user = authService.getCurrentlyAuthentificatedUser();
        PlayList playList = findByIdAndUser(id, user);
        updateFields(playList, playListRequestDto);
    }

    private void updateFields(PlayList playList, PlayListRequestDto playListRequestDto) {
        playList.setTitle(playListRequestDto.getTitle());
    }
}
