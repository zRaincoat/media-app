package org.stefan.media_app.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.stefan.media_app.models.User;
import org.stefan.media_app.models.Video;

public interface VideoRepository extends JpaRepository<Video, UUID> {
    @Query("""
            SELECT v
            FROM Video v
            LEFT JOIN FETCH v.playList pl
            LEFT JOIN FETCH pl.author a
            WHERE v.id = :videoId
              AND a = :user
              AND v.deletedAt IS NULL
            """)
    Optional<Video> findByIdAndUser(UUID videoId, User user);

    @Query("""
            SELECT v
            FROM Video v
            LEFT JOIN FETCH v.playList pl
            LEFT JOIN FETCH pl.author a
            WHERE v.id = :id
              AND v.deletedAt IS NULL
            """)
    Optional<Video> findByIdAndDeletedAtIsNull(UUID id);

    @Query("""
            SELECT COUNT(v)
            FROM Video v
            WHERE v.playList.id = :id
              AND v.deletedAt IS NULL
            """)
    int getPlayListVideoCount(UUID id);

    @Query("""
            SELECT v
            FROM Video v
            LEFT JOIN FETCH v.playList pl
            LEFT JOIN pl.author a
            WHERE a.id = :userId
              AND v.deletedAt IS NULL
            """)
    List<Video> getVideosWithPlayListsByUserId(UUID userId);

    @Query("""
            SELECT v
            FROM Video v
            WHERE v.playList.id = :playListId
              AND v.deletedAt IS NULL
              AND v.playList.deletedAt IS NULL
            """)
    List<Video> findByPlayListId(UUID playListId);
}
