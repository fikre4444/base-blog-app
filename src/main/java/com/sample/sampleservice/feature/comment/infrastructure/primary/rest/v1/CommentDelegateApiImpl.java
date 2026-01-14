package com.sample.sampleservice.feature.comment.infrastructure.primary.rest.v1;

import com.sample.sampleservice.feature.comment.api.rest.v1.CommentsApiDelegate;
import com.sample.sampleservice.feature.comment.api.rest.v1.model.CommentDetail;
import com.sample.sampleservice.feature.comment.api.rest.v1.model.CommentDetailPaginated;
import com.sample.sampleservice.feature.comment.api.rest.v1.model.CommentRequest;
import com.sample.sampleservice.feature.comment.application.CommentApplicationService;
import com.sample.sampleservice.feature.comment.domain.model.Comment;
import com.sample.sampleservice.feature.comment.infrastructure.primary.mapper.CommentModelMapper;
import com.sample.sampleservice.shared.pagination.domain.Page;
import com.sample.sampleservice.shared.pagination.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentDelegateApiImpl implements CommentsApiDelegate {

        private final CommentApplicationService commentApplicationService;
        private final CommentModelMapper commentModelMapper;

        @Override
        public ResponseEntity<CommentDetail> createComment(CommentRequest commentRequest) {
                Comment comment = commentApplicationService.createComment(
                                commentRequest.getBlogId(),
                                commentRequest.getMessage(),
                                commentRequest.getReplyCommentId());
                return ResponseEntity.status(HttpStatus.CREATED).body(commentModelMapper.toDto(comment));
        }

        @Override
        public ResponseEntity<CommentDetail> replyToComment(
                        com.sample.sampleservice.feature.comment.api.rest.v1.model.ReplyRequest replyRequest) {
                Comment comment = commentApplicationService.replyToComment(
                                replyRequest.getCommentId(),
                                replyRequest.getMessage());
                return ResponseEntity.status(HttpStatus.CREATED).body(commentModelMapper.toDto(comment));
        }

        @Override
        public ResponseEntity<CommentDetailPaginated> getCommentsByBlogId(String blogId, Optional<Integer> page,
                        Optional<Integer> limit) {
                Pageable pageable = Pageable.builder()
                                .page(page.orElse(1))
                                .pageSize(limit.orElse(20))
                                .build();

                Page<Comment> comments = commentApplicationService.getCommentsByBlogId(blogId, pageable);

                CommentDetailPaginated response = new CommentDetailPaginated()
                                .contents(commentModelMapper.toDto(comments.getContent()))
                                .total(comments.getTotal())
                                .totalPages(comments.getTotalPages())
                                .currentPage((int) comments.getCurrentPage())
                                .hasNext(comments.isHasNext())
                                .hasPrevious(comments.isHasPrevious())
                                .isLast(comments.isLast());

                return ResponseEntity.ok(response);
        }
}
