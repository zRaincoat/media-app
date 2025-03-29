package org.stefan.media_app.services.impl;

import java.util.List;
import org.springframework.stereotype.Component;
import org.stefan.media_app.models.User;
import org.stefan.media_app.models.Video;
import org.stefan.media_app.services.VideoNotificationObserver;

@Component
public class VideoEventSubject {

    private final List<VideoNotificationObserver> observers;

    public VideoEventSubject(List<VideoNotificationObserver> observers) {
        this.observers = observers;
    }

    public void notifyVideoLiked(Video video, User user) {
        observers.forEach(observer -> observer.onVideoLiked(video, user));
    }

    public void notifyVideoDisliked(Video video, User user) {
        observers.forEach(observer -> observer.onVideoDisliked(video, user));
    }
}