package org.stefan.media_app.controllers;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.stefan.media_app.dtos.requests.PlayListRequestDto;
import org.stefan.media_app.dtos.responses.PlayListResponseDto;
import org.stefan.media_app.services.PlayListService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/play-lists")
public class PlayListController {

    private final PlayListService playListService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPlayList(@Valid @RequestBody PlayListRequestDto playListRequestDto) {
        log.info("Entering POST /play-lists");
        playListService.addPlayList(playListRequestDto);
    }

    @GetMapping("/{id}")
    public PlayListResponseDto getPlayListInfo(@PathVariable UUID id) {
        log.info("Entering GET /play-lists/{}", id);
        return playListService.getPlayListInfo(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlayList(@PathVariable UUID id) {
        log.info("Entering DELETE /play-lists/{}", id);
        playListService.deletePlayList(id);
    }

    @PutMapping("/{id}")
    public void updatePlayList(@PathVariable UUID id, @RequestBody PlayListRequestDto playListRequestDto) {
        log.info("Entering PUT /play-lists/{}", id);
        playListService.updatePlayList(id, playListRequestDto);
    }
}
