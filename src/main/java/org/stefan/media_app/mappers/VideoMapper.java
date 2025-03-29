package org.stefan.media_app.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.stefan.media_app.dtos.requests.VideoRequestDto;
import org.stefan.media_app.dtos.responses.VideoFullInfoResponseDto;
import org.stefan.media_app.dtos.responses.VideoLowInfoResponseDto;
import org.stefan.media_app.dtos.responses.VideoResponseDto;
import org.stefan.media_app.models.PlayList;
import org.stefan.media_app.models.Video;

@Component
@RequiredArgsConstructor
public class VideoMapper {

    private final UserMapper userMapper;
    private final PlayListMapper playListMapper;

    public Video mapToModel(VideoRequestDto videoRequestDto, PlayList playList) {
        if (videoRequestDto == null) {
            return null;
        }
        return Video.builder()
                .title(videoRequestDto.getTitle())
                .description(videoRequestDto.getDescription())
                .url(videoRequestDto.getUrl())
                .categories(videoRequestDto.getCategories())
                .playList(playList)
                .build();
    }

    public VideoResponseDto mapToResponseDto(Video video) {
        if (video == null) {
            return null;
        }
        return VideoResponseDto.builder()
                .id(video.getId())
                .title(video.getTitle())
                .url(video.getUrl())
                .author(userMapper.mapToLowInfoResponseDto(video.getPlayList().getAuthor()))
                .categories(video.getCategories())
                .likes(video.getLikes())
                .dislikes(video.getDislikes())
                .createdAt(video.getCreatedAt() == null ? null : video.getCreatedAt().toString())
                .updatedAt(video.getUpdatedAt() == null ? null : video.getUpdatedAt().toString())
                .build();
    }

    public VideoFullInfoResponseDto mapToFullInfoResponseDto(Video video, int playListVideoCount) {
        if (video == null) {
            return null;
        }
        return VideoFullInfoResponseDto.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .url(video.getUrl())
                .playList(playListMapper.mapToResponseDto(video.getPlayList(), playListVideoCount))
                .author(userMapper.mapToLowInfoResponseDto(video.getPlayList().getAuthor()))
                .categories(video.getCategories())
                .likes(video.getLikes())
                .dislikes(video.getDislikes())
                .createdAt(video.getCreatedAt() == null ? null : video.getCreatedAt().toString())
                .updatedAt(video.getUpdatedAt() == null ? null : video.getUpdatedAt().toString())
                .build();
    }

    public VideoLowInfoResponseDto mapToLowInfoResponseDto(Video video) {
        if (video == null) {
            return null;
        }

        return VideoLowInfoResponseDto.builder()
                .id(video.getId())
                .title(video.getTitle())
                .url(video.getUrl())
                .build();
    }
}
