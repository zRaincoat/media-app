package org.stefan.media_app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.stefan.media_app.enums.Category;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "videos")
public class Video extends AbstractEntity {
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String url;

    @ManyToOne
    private PlayList playList;

    @Builder.Default
    private long likes = 0L;
    @Builder.Default
    private long dislikes = 0L;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    public void like() {
        likes++;
    }

    public void dislike() {
        dislikes++;
    }
}
