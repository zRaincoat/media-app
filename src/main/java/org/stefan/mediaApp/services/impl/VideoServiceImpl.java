package org.stefan.mediaApp.services.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stefan.mediaApp.dtos.requests.VideoRequestDto;
import org.stefan.mediaApp.dtos.requests.VideoUpdateRequestDto;
import org.stefan.mediaApp.dtos.responses.VideoFullInfoResponseDto;
import org.stefan.mediaApp.mappers.VideoMapper;
import org.stefan.mediaApp.models.PlayList;
import org.stefan.mediaApp.models.User;
import org.stefan.mediaApp.models.Video;
import org.stefan.mediaApp.repositories.VideoRepository;
import org.stefan.mediaApp.services.AuthService;
import org.stefan.mediaApp.services.PlayListService;
import org.stefan.mediaApp.services.VideoService;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final PlayListService playListService;
    private final AuthService authService;

    @Override
    @Transactional
    public void addVideo(VideoRequestDto videoRequestDto) {
        User user = authService.getCurrentlyAuthentificatedUser();
        PlayList playList = playListService.findByIdAndUser(videoRequestDto.getPlayListId(), user);
        Video video = videoMapper.mapToModel(videoRequestDto, playList);
        videoRepository.save(video);
    }

    @Override
    @Transactional
    public void updateVideo(UUID id, VideoUpdateRequestDto videoRequestDto) {
        User user = authService.getCurrentlyAuthentificatedUser();
        Video video = findByIdAndUser(id, user);
        video.setTitle(videoRequestDto.getTitle());
        video.setDescription(videoRequestDto.getDescription());
        video.setUrl(videoRequestDto.getUrl());
        video.setCategories(videoRequestDto.getCategories());
    }

    @Override
    public Video findByIdAndUser(UUID videoId, User user) {
        return videoRepository.findByIdAndUser(videoId, user)
                .orElseThrow(() -> new EntityNotFoundException("Video with id " + videoId + " not found"));
    }

    @Override
    @Transactional
    public void deleteVideo(UUID id) {
        User user = authService.getCurrentlyAuthentificatedUser();
        Video video = findByIdAndUser(id, user);
        video.softDelete();
    }

    @Override
    @Transactional
    public void likeVideo(UUID id) {
        Video video = getVideoById(id);
        video.like();
    }

    @Override
    public Video getVideoById(UUID id) {
        return videoRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException("Video with id " + id + " not found"));
    }

    @Override
    @Transactional
    public void dislikeVideo(UUID id) {
        Video video = getVideoById(id);
        video.dislike();
    }

    @Override
    @Transactional(readOnly = true)
    public VideoFullInfoResponseDto getVideo(UUID id) {
        Video video = getVideoById(id);
        int playListVideoCount = videoRepository.getPlayListVideoCount(video.getPlayList().getId());
        return videoMapper.mapToFullInfoResponseDto(video, playListVideoCount);
    }
}
