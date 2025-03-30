package org.stefan.media_app;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stefan.media_app.enums.VideoSortBy;
import org.stefan.media_app.factories.VideoFactory;
import org.stefan.media_app.models.Video;
import org.stefan.media_app.services.VideoSortStrategy;
import org.stefan.media_app.services.impl.VideoSortContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoSortContextTest {

    @Mock
    private VideoSortStrategy likesAscStrategy;

    @Mock
    private VideoSortStrategy likesDescStrategy;

    @Mock
    private VideoSortStrategy updatedAtAscStrategy;

    @Mock
    private VideoSortStrategy updatedAtDescStrategy;

    private VideoSortContext videoSortContext;

    @BeforeEach
    void setUp() {
        Map<String, VideoSortStrategy> strategies = new HashMap<>();
        strategies.put(VideoSortBy.LIKES_ASC.name(), likesAscStrategy);
        strategies.put(VideoSortBy.LIKES_DESC.name(), likesDescStrategy);
        strategies.put(VideoSortBy.UPDATED_AT_ASC.name(), updatedAtAscStrategy);
        strategies.put(VideoSortBy.UPDATED_AT_DESC.name(), updatedAtDescStrategy);

        videoSortContext = new VideoSortContext(strategies);
    }

    @Test
    void shouldReturnVideosSortedByLikesAsc() {
        Video v1 = VideoFactory.createVideo();
        Video v2 = VideoFactory.createVideo();
        v1.setLikes(10);
        v2.setLikes(20);
        List<Video> mockVideos = List.of(v1, v2);

        when(likesAscStrategy.sortVideos()).thenReturn(mockVideos);

        List<Video> sortedVideos = videoSortContext.getSortedVideos(VideoSortBy.LIKES_ASC);

        assertEquals(2, sortedVideos.size());
        assertTrue(sortedVideos.get(0).getLikes() < sortedVideos.get(1).getLikes());

        verify(likesAscStrategy).sortVideos();
    }

    @Test
    void shouldReturnVideosSortedByLikesDesc() {
        Video v1 = VideoFactory.createVideo();
        Video v2 = VideoFactory.createVideo();
        v1.setLikes(30);
        v2.setLikes(20);
        List<Video> mockVideos = List.of(v1, v2);

        when(likesDescStrategy.sortVideos()).thenReturn(mockVideos);

        List<Video> sortedVideos = videoSortContext.getSortedVideos(VideoSortBy.LIKES_DESC);

        assertEquals(2, sortedVideos.size());
        assertTrue(sortedVideos.get(0).getLikes() > sortedVideos.get(1).getLikes());

        verify(likesDescStrategy).sortVideos();
    }

    @Test
    void shouldReturnVideosSortedByUpdatedAtAsc() {
        Video v1 = VideoFactory.createVideo();
        Video v2 = VideoFactory.createVideo();
        v1.setUpdatedAt(LocalDateTime.now().minusDays(2));
        v2.setUpdatedAt(LocalDateTime.now().minusDays(1));
        List<Video> mockVideos = List.of(v1, v2);

        when(updatedAtAscStrategy.sortVideos()).thenReturn(mockVideos);

        List<Video> sortedVideos = videoSortContext.getSortedVideos(VideoSortBy.UPDATED_AT_ASC);

        assertEquals(2, sortedVideos.size());
        assertTrue(sortedVideos.get(0).getUpdatedAt().isBefore(sortedVideos.get(1).getUpdatedAt()));

        verify(updatedAtAscStrategy).sortVideos();
    }

    @Test
    void shouldReturnVideosSortedByUpdatedAtDesc() {
        Video v1 = VideoFactory.createVideo();
        v1.setUpdatedAt(LocalDateTime.now());
        Video v2 = VideoFactory.createVideo();
        v2.setUpdatedAt(LocalDateTime.now().minusDays(1));
        List<Video> mockVideos = List.of(v1, v2);

        when(updatedAtDescStrategy.sortVideos()).thenReturn(mockVideos);

        List<Video> sortedVideos = videoSortContext.getSortedVideos(VideoSortBy.UPDATED_AT_DESC);

        assertEquals(2, sortedVideos.size());
        assertTrue(sortedVideos.get(0).getUpdatedAt().isAfter(sortedVideos.get(1).getUpdatedAt()));

        verify(updatedAtDescStrategy).sortVideos();
    }
}
