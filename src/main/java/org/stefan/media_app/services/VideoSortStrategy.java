package org.stefan.media_app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.stefan.media_app.models.Video;

public interface VideoSortStrategy {
    Page<Video> sortVideos(Pageable pageable);
}