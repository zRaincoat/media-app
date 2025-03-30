package org.stefan.media_app.services.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stefan.media_app.dtos.requests.VideoRequestDto;
import org.stefan.media_app.dtos.requests.VideoUpdateRequestDto;
import org.stefan.media_app.dtos.responses.VideoFullInfoResponseDto;
import org.stefan.media_app.dtos.responses.VideoResponseDto;
import org.stefan.media_app.enums.VideoSortBy;
import org.stefan.media_app.mappers.VideoMapper;
import org.stefan.media_app.models.PlayList;
import org.stefan.media_app.models.User;
import org.stefan.media_app.models.Video;
import org.stefan.media_app.repositories.VideoRepository;
import org.stefan.media_app.services.AuthService;
import org.stefan.media_app.services.PlayListService;
import org.stefan.media_app.services.VideoService;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final PlayListService playListService;
    private final AuthService authService;
    private final VideoEventSubject videoEventSubject;
    private final VideoSortContext videoSortContext;

    @Override
    @Transactional
    public void addVideo(VideoRequestDto videoRequestDto) {
        User user = authService.getCurrentlyAuthenticatedUser();
        PlayList playList = playListService.findByIdAndUser(videoRequestDto.getPlayListId(), user);
        Video video = videoMapper.mapToModel(videoRequestDto, playList);
        videoRepository.save(video);
    }

    @Override
    @Transactional
    public void updateVideo(UUID id, VideoUpdateRequestDto videoRequestDto) {
        User user = authService.getCurrentlyAuthenticatedUser();
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
        User user = authService.getCurrentlyAuthenticatedUser();
        Video video = findByIdAndUser(id, user);
        video.softDelete();
    }

    @Override
    @Transactional
    public void likeVideo(UUID id) {
        User user = authService.getCurrentlyAuthenticatedUser();
        Video video = getVideoById(id);
        video.like();
        videoEventSubject.notifyVideoLiked(video, user);
    }

    @Override
    public Video getVideoById(UUID id) {
        return videoRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException("Video with id " + id + " not found"));
    }

    @Override
    @Transactional
    public void dislikeVideo(UUID id) {
        User user = authService.getCurrentlyAuthenticatedUser();
        Video video = getVideoById(id);
        video.dislike();
        videoEventSubject.notifyVideoDisliked(video, user);
    }

    @Override
    @Transactional(readOnly = true)
    public VideoFullInfoResponseDto getVideo(UUID id) {
        Video video = getVideoById(id);
        int playListVideoCount = videoRepository.getPlayListVideoCount(video.getPlayList().getId());
        return videoMapper.mapToFullInfoResponseDto(video, playListVideoCount);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VideoResponseDto> getAllVideos(VideoSortBy sort, Pageable pageable) {
        List<Video> videos = videoSortContext.getSortedVideos(sort);
        Page<Video> videoPage = new PageImpl<>(videos, pageable, videos.size());
        videoRepository.fetchWithPlayListsAndAuthors(videoPage.getContent());
        return videoPage.map(videoMapper::mapToResponseDto);
    }
}
