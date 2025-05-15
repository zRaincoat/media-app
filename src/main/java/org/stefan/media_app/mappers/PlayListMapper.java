package org.stefan.media_app.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.stefan.media_app.dtos.requests.PlayListRequestDto;
import org.stefan.media_app.dtos.responses.PlayListLowInfoResponseDto;
import org.stefan.media_app.models.PlayList;
import org.stefan.media_app.models.User;

@Component
@RequiredArgsConstructor
public class PlayListMapper {

    public PlayList mapToModel(PlayListRequestDto playListRequestDto, User user) {
        if (playListRequestDto == null) {
            return null;
        }
        return PlayList.builder()
                .title(playListRequestDto.getTitle())
                .author(user)
                .build();
    }

    public PlayListLowInfoResponseDto mapToResponseDto(PlayList playList, int playListVideoCount) {
        if (playList == null) {
            return null;
        }
        return PlayListLowInfoResponseDto.builder()
                .id(playList.getId())
                .title(playList.getTitle())
                .createdAt(playList.getCreatedAt())
                .updatedAt(playList.getUpdatedAt())
                .videoCount(playListVideoCount)
                .build();
    }
}
