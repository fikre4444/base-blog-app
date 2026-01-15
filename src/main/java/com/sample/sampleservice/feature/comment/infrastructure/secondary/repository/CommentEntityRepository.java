package com.sample.sampleservice.feature.comment.infrastructure.secondary.repository;

import com.sample.sampleservice.feature.comment.infrastructure.secondary.domain.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentEntityRepository
        extends JpaRepository<CommentEntity, String>, JpaSpecificationExecutor<CommentEntity> {
    Page<CommentEntity> findAllByBlogId(String blogId, Pageable pageable);

    @Query("SELECT c FROM CommentEntity c WHERE c.replyCommentId = ?1")
    Page<CommentEntity> findReplyCommentByCommentId(String commentId, Pageable pageable);
}
