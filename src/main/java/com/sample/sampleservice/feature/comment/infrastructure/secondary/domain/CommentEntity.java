package com.sample.sampleservice.feature.comment.infrastructure.secondary.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.ZonedDateTime;

@Entity
@Table(name = "t_comment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {

    @Id
    @UuidGenerator
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "blog_id", nullable = false)
    private String blogId;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "reply_comment_id")
    private String replyCommentId;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
}
