package com.sample.sampleservice.feature.comment.application;

import com.sample.sampleservice.feature.comment.domain.exception.CommentErrorKey;
import com.sample.sampleservice.feature.comment.domain.model.Comment;
import com.sample.sampleservice.feature.comment.domain.repository.CommentRepository;
import com.sample.sampleservice.shared.authentication.application.AuthenticatedUser;
import com.sample.sampleservice.shared.error.domain.GeneratorException;
import com.sample.sampleservice.shared.pagination.domain.Page;
import com.sample.sampleservice.shared.pagination.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class CommentApplicationService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(String blogId, String message, String replyCommentId) {
        String userId = AuthenticatedUser.getUser().id();

        Comment comment = Comment.builder()
                .userId(userId)
                .blogId(blogId)
                .message(message)
                .replyCommentId(replyCommentId)
                .createdAt(ZonedDateTime.now())
                .build();

        return commentRepository.save(comment);
    }

    @Transactional
    public Comment replyToComment(String parentCommentId, String message) {
        Comment parentComment = getComment(parentCommentId);
        String userId = AuthenticatedUser.getUser().id();

        Comment reply = Comment.builder()
                .userId(userId)
                .blogId(parentComment.getBlogId())
                .message(message)
                .replyCommentId(parentCommentId)
                .createdAt(ZonedDateTime.now())
                .build();

        return commentRepository.save(reply);
    }

    public Page<Comment> getCommentsByBlogId(String blogId, Pageable pageable) {
        return commentRepository.findByBlogId(blogId, pageable);
    }

    public Comment getComment(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> GeneratorException.badRequest(CommentErrorKey.COMMENT_NOT_FOUND).build());
    }

    @Transactional
    public void deleteComment(String id) {
        Comment existingComment = getComment(id);
        String currentUserId = AuthenticatedUser.getUser().id();

        if (!existingComment.getUserId().equals(currentUserId)) {
            throw GeneratorException.badRequest(CommentErrorKey.COMMENT_UNAUTHORIZED)
                    .message("You cannot delete someone else's comment").build();
        }

        commentRepository.deleteById(id);
    }
}
