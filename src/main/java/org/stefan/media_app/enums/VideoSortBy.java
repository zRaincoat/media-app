package org.stefan.media_app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@Getter
public enum VideoSortBy {
    LIKES_ASC(Sort.by(Sort.Direction.ASC, "likes")),
    LIKES_DESC(Sort.by(Sort.Direction.DESC, "likes")),
    UPDATED_AT_ASC(Sort.by(Sort.Direction.ASC, "updatedAt")),
    UPDATED_AT_DESC(Sort.by(Sort.Direction.DESC, "updatedAt"));

    private final Sort sort;
}