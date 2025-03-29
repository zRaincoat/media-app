package org.stefan.mediaApp.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.stefan.mediaApp.dtos.requests.PlayListRequestDto;
import org.stefan.mediaApp.dtos.responses.PlayListLowInfoResponseDto;
import org.stefan.mediaApp.models.PlayList;
import org.stefan.mediaApp.models.User;

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
                .createdAt(playList.getCreatedAt() == null ? null : playList.getCreatedAt().toString())
                .updatedAt(playList.getUpdatedAt() == null ? null : playList.getUpdatedAt().toString())
                .videoCount(playListVideoCount)
                .build();
    }
}
