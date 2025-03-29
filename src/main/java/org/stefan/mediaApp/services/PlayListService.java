package org.stefan.mediaApp.services;

import java.util.List;
import java.util.UUID;
import org.stefan.mediaApp.dtos.requests.PlayListRequestDto;
import org.stefan.mediaApp.dtos.responses.PlayListResponseDto;
import org.stefan.mediaApp.models.PlayList;
import org.stefan.mediaApp.models.User;

public interface PlayListService {
    List<PlayListResponseDto> getAllPlayListsInfoByUser(UUID userId);

    PlayListResponseDto getPlayListInfo(UUID playListId);

    PlayList findByIdAndUser(UUID playListId, User user);

    void addPlayList(PlayListRequestDto playListRequestDto);

    void deletePlayList(UUID id);

    void updatePlayList(UUID id, PlayListRequestDto playListRequestDto);
}
