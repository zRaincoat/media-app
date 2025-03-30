package org.stefan.media_app.services.impl.video_sort_strategies;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.stefan.media_app.enums.VideoSortBy;
import org.stefan.media_app.models.Video;
import org.stefan.media_app.repositories.VideoRepository;
import org.stefan.media_app.services.VideoSortStrategy;

@Component("UPDATED_AT_DESC")
@RequiredArgsConstructor
public class VideoSortUpdatedAtDescStrategy implements VideoSortStrategy {

    private final VideoRepository videoRepository;

    @Override
    public List<Video> sortVideos() {
        return videoRepository.findAllSorted(VideoSortBy.UPDATED_AT_DESC.getSort());
    }
}
