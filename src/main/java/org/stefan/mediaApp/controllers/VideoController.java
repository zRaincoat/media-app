package org.stefan.mediaApp.controllers;

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
import org.stefan.mediaApp.dtos.requests.VideoRequestDto;
import org.stefan.mediaApp.dtos.requests.VideoUpdateRequestDto;
import org.stefan.mediaApp.dtos.responses.VideoFullInfoResponseDto;
import org.stefan.mediaApp.services.VideoService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addVideo(@Valid @RequestBody final VideoRequestDto videoRequestDto) {
        log.info("Entering POST /videos");
        videoService.addVideo(videoRequestDto);
    }

    @PutMapping("/{id}")
    public void updateVideo(@PathVariable UUID id,
                            @RequestBody final VideoUpdateRequestDto videoRequestDto) {
        log.info("Entering PUT /videos/{}", id);
        videoService.updateVideo(id, videoRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVideo(@PathVariable UUID id) {
        log.info("Entering DELETE /videos/{}", id);
        videoService.deleteVideo(id);
    }

    @PostMapping("/{id}/likes")
    public void likeVideo(@PathVariable UUID id) {
        log.info("Entering POST /videos/{}/like", id);
        videoService.likeVideo(id);
    }

    @PostMapping("/{id}/dislikes")
    public void dislikeVideo(@PathVariable UUID id) {
        log.info("Entering POST /videos/{}/dislike", id);
        videoService.dislikeVideo(id);
    }

    @GetMapping("/{id}")
    public VideoFullInfoResponseDto getVideo(@PathVariable UUID id) {
        log.info("Entering GET /videos/{}", id);
        return videoService.getVideo(id);
    }
}
