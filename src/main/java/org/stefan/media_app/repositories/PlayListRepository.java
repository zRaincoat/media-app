package org.stefan.media_app.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.stefan.media_app.dtos.responses.PlayListLowInfoResponseDto;
import org.stefan.media_app.dtos.responses.PlayListResponseDto;
import org.stefan.media_app.models.PlayList;
import org.stefan.media_app.models.User;

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, UUID> {
    @Query("""
            SELECT new org.stefan.media_app.dtos.responses.PlayListResponseDto(
                p.id,
                p.title,
                p.createdAt,
                p.updatedAt,
                CAST(COUNT(v.id) AS int),
                null
            )
            FROM PlayList p
            LEFT JOIN p.videoList v
            LEFT JOIN p.author a
            WHERE a.id = :userId
              AND a.deletedAt IS NULL
              AND p.deletedAt IS NULL
            GROUP BY p.id, p.title, p.createdAt, p.updatedAt
            """)
    List<PlayListResponseDto> getPlayListsInfoByUser(UUID userId);

    @Query("""
            SELECT p
            FROM PlayList p
            LEFT JOIN FETCH p.author a
            LEFT JOIN FETCH p.videoList v
            WHERE p.id = :playListId
              AND a = :user
              AND p.deletedAt IS NULL
            """)
    Optional<PlayList> findByIdAndUser(UUID playListId, User user);

    @Query("""
            SELECT new org.stefan.media_app.dtos.responses.PlayListResponseDto(
                p.id,
                p.title,
                p.createdAt,
                p.updatedAt,
                CAST(COUNT(v.id) AS int),
                null
            )
            FROM PlayList p
            LEFT JOIN p.videoList v
            WHERE p.id = :playListId
              AND p.deletedAt IS NULL
            GROUP BY p.id, p.title, p.createdAt, p.updatedAt
            """)
    Optional<PlayListResponseDto> getPlayListInfoById(UUID playListId);

    @Query("""
            SELECT new org.stefan.media_app.dtos.responses.PlayListLowInfoResponseDto(
                pl.id,
                pl.title,
                pl.createdAt,
                pl.updatedAt,
                COALESCE(COUNT(vl), 0)
            )
            FROM PlayList pl
            LEFT JOIN pl.videoList vl
            ON vl.deletedAt IS NULL
            WHERE pl.author = :user
            AND pl.deletedAt IS NULL
            GROUP BY pl.id
            """)
    Page<PlayListLowInfoResponseDto> findAllByUser(Pageable pageable, User user);
}
