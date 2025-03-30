package org.stefan.media_app.factories;

import org.stefan.media_app.models.Video;

public class VideoFactory {
    public static Video createVideo() {
        return Video.builder()
                .url("url")
                .title("title")
                .description("description")
                .likes(13L)
                .dislikes(1L)
                .build();
    }
}
