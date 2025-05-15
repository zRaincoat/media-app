package org.stefan.media_app.services.impl.video_sort_strategies;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.stefan.media_app.enums.VideoSortBy;
import org.stefan.media_app.models.Video;
import org.stefan.media_app.repositories.VideoRepository;
import org.stefan.media_app.services.VideoSortStrategy;

@Component("LIKES_DESC")
@RequiredArgsConstructor
public class VideoSortLikesDescStrategy implements VideoSortStrategy {
    private final VideoRepository videoRepository;

    @Override
    public Page<Video> sortVideos(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                VideoSortBy.LIKES_DESC.getSort()
        );
        return videoRepository.findAllSorted(sortedPageable);
    }
}