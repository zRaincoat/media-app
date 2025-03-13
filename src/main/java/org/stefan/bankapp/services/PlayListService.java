package org.stefan.bankapp.services;

import java.util.List;
import java.util.UUID;
import org.stefan.bankapp.dtos.requests.PlayListRequestDto;
import org.stefan.bankapp.dtos.responses.PlayListResponseDto;
import org.stefan.bankapp.models.PlayList;
import org.stefan.bankapp.models.User;

public interface PlayListService {
    List<PlayListResponseDto> getAllPlayListsInfoByUser(UUID userId);

    PlayListResponseDto getPlayListInfo(UUID playListId);

    PlayList findByIdAndUser(UUID playListId, User user);

    void addPlayList(PlayListRequestDto playListRequestDto);

    void deletePlayList(UUID id);

    void updatePlayList(UUID id, PlayListRequestDto playListRequestDto);
}
