package org.stefan.bankapp.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.stefan.bankapp.dtos.requests.PlayListRequestDto;
import org.stefan.bankapp.dtos.responses.PlayListLowInfoResponseDto;
import org.stefan.bankapp.models.PlayList;
import org.stefan.bankapp.models.User;

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
