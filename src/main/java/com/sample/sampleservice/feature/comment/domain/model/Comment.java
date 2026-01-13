package com.sample.sampleservice.feature.comment.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class Comment {
    private String id;
    private String userId;
    private String blogId;
    private String message;
    private String replyCommentId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
