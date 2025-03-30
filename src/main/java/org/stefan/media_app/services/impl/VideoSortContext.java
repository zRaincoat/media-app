package org.stefan.media_app.services.impl;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.stefan.media_app.enums.VideoSortBy;
import org.stefan.media_app.models.Video;
import org.stefan.media_app.services.VideoSortStrategy;

@Service
@RequiredArgsConstructor
public class VideoSortContext {

    private final Map<String, VideoSortStrategy> strategies;

    public List<Video> getSortedVideos(VideoSortBy sortBy) {
        VideoSortStrategy strategy = strategies.get(sortBy.name());
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for: " + sortBy);
        }
        return strategy.sortVideos();
    }
}