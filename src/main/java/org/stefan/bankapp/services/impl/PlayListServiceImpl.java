package org.stefan.bankapp.services.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stefan.bankapp.dtos.requests.PlayListRequestDto;
import org.stefan.bankapp.dtos.responses.PlayListResponseDto;
import org.stefan.bankapp.dtos.responses.VideoResponseDto;
import org.stefan.bankapp.mappers.PlayListMapper;
import org.stefan.bankapp.mappers.VideoMapper;
import org.stefan.bankapp.models.AbstractEntity;
import org.stefan.bankapp.models.PlayList;
import org.stefan.bankapp.models.User;
import org.stefan.bankapp.models.Video;
import org.stefan.bankapp.repositories.PlayListRepository;
import org.stefan.bankapp.services.AuthService;
import org.stefan.bankapp.services.PlayListService;
import org.stefan.bankapp.services.VideoService;

@Service
@RequiredArgsConstructor
public class PlayListServiceImpl implements PlayListService {

    private final PlayListRepository playListRepository;
    private final AuthService authService;
    private final PlayListMapper playListMapper;
    private final VideoMapper videoMapper;

    @Autowired
    @Lazy
    private VideoService videoService;

    @Override
    @Transactional(readOnly = true)
    public List<PlayListResponseDto> getAllPlayListsInfoByUser(UUID userId) {
        List<PlayListResponseDto> responseDtos = playListRepository.getPlayListsInfoByUser(userId);
        Map<UUID, List<Video>> videoMap = videoService.getPlayListIdsToVideosByUserId(userId);
        responseDtos.forEach(playListResponseDto -> {
            List<VideoResponseDto> videoResponseDtos = videoMap.getOrDefault(playListResponseDto.getId(), new ArrayList<>()).stream()
                    .map(videoMapper::mapToResponseDto)
                    .toList();
            playListResponseDto.setVideos(videoResponseDtos);
        });
        return responseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public PlayListResponseDto getPlayListInfo(UUID playListId) {
        PlayListResponseDto responseDto =  playListRepository.getPlayListInfoById(playListId)
                .orElseThrow(() -> new EntityNotFoundException("PlayList with id " + playListId + " not found"));
        List<VideoResponseDto> videoResponseDtos = videoService.getVideosInfoByPlayListId(playListId);
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
