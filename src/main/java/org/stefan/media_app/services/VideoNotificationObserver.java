package org.stefan.media_app.services;

import org.stefan.media_app.models.User;
import org.stefan.media_app.models.Video;

public interface VideoNotificationObserver {

    void onVideoLiked(Video video, User user);

    void onVideoDisliked(Video video, User user);
}
