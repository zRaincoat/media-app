package org.stefan.media_app.services;

import java.util.List;
import org.stefan.media_app.models.Video;

public interface VideoSortStrategy {
    List<Video> sortVideos();
}