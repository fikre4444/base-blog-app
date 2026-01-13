package com.sample.sampleservice.feature.comment.domain.exception;

import com.sample.sampleservice.shared.error.domain.ErrorKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentErrorKey implements ErrorKey {
    COMMENT_NOT_FOUND("comment_not_found"),
    COMMENT_UNAUTHORIZED("comment_unauthorized");

    private final String key;

    public String get() {
        return key;
    }
}
