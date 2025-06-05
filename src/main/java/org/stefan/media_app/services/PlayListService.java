package org.stefan.media_app.services;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.stefan.media_app.dtos.requests.PlayListRequestDto;
import org.stefan.media_app.dtos.responses.PlayListLowInfoResponseDto;
import org.stefan.media_app.dtos.responses.PlayListResponseDto;
import org.stefan.media_app.models.PlayList;
import org.stefan.media_app.models.User;

public interface PlayListService {
    List<PlayListResponseDto> getAllPlayListsInfoByUser(UUID userId);

    PlayListResponseDto getPlayListInfo(UUID playListId);

    PlayList findByIdAndUser(UUID playListId, User user);

    void addPlayList(PlayListRequestDto playListRequestDto);

    void deletePlayList(UUID id);

    void updatePlayList(UUID id, PlayListRequestDto playListRequestDto);

    Page<PlayListLowInfoResponseDto> getPlayListsByAuthUser(Pageable pageable);
}
