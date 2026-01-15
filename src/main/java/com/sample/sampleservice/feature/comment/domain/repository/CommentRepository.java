package com.sample.sampleservice.feature.comment.domain.repository;

import com.sample.sampleservice.feature.comment.domain.model.Comment;
import com.sample.sampleservice.shared.pagination.domain.Page;
import com.sample.sampleservice.shared.pagination.domain.Pageable;

import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(String id);

    Page<Comment> findByBlogId(String blogId, Pageable pageable);

    Page<Comment> findReplyCommentByCommentId(String commentId, Pageable pageable);

    void deleteById(String id);
}
