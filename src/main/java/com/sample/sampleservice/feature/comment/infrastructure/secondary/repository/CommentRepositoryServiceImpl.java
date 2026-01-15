package com.sample.sampleservice.feature.comment.infrastructure.secondary.repository;

import com.sample.sampleservice.feature.comment.domain.model.Comment;
import com.sample.sampleservice.feature.comment.domain.repository.CommentRepository;
import com.sample.sampleservice.feature.comment.infrastructure.secondary.domain.CommentEntity;
import com.sample.sampleservice.feature.comment.infrastructure.secondary.mapper.CommentEntityMapper;
import com.sample.sampleservice.shared.pagination.domain.Page;
import com.sample.sampleservice.shared.pagination.domain.Pageable;
import lombok.RequiredArgsConstructor;

import org.hibernate.query.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryServiceImpl implements CommentRepository {

    private final CommentEntityRepository commentEntityRepository;
    private final CommentEntityMapper commentEntityMapper;

    @Override
    public Comment save(Comment comment) {
        CommentEntity entity = commentEntityMapper.toEntity(comment);
        CommentEntity savedEntity = commentEntityRepository.save(entity);
        return commentEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Comment> findById(String id) {
        return commentEntityRepository.findById(id).map(commentEntityMapper::toDomain);
    }

    @Override
    public Page<Comment> findByBlogId(String blogId, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest springPageable = PageRequest.of(pageable.getPage() - 1, pageable.getPageSize(), sort);

        org.springframework.data.domain.Page<CommentEntity> result = commentEntityRepository.findAllByBlogId(blogId,
                springPageable);

        return new Page<Comment>()
                .content(result.getContent().stream().map(commentEntityMapper::toDomain).toList())
                .currentPage(result.getNumber() + 1)
                .total((int) result.getTotalElements())
                .totalPages(result.getTotalPages())
                .hasNext(result.hasNext())
                .hasPrevious(result.hasPrevious())
                .isLast(result.isLast());
    }

    @Override
    public Page<Comment> findReplyCommentByCommentId(String commentId, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest springPageable = PageRequest.of(pageable.getPage() - 1, pageable.getPageSize(), sort);
        org.springframework.data.domain.Page<CommentEntity> result = commentEntityRepository.findReplyCommentByCommentId(commentId,
                springPageable);
        return new Page<Comment>()
                .content(result.getContent().stream().map(commentEntityMapper::toDomain).toList())
                .currentPage(result.getNumber() + 1)
                .total((int) result.getTotalElements())
                .totalPages(result.getTotalPages())
                .hasNext(result.hasNext())
                .hasPrevious(result.hasPrevious())
                .isLast(result.isLast());
    }

    @Override
    public void deleteById(String id) {
        commentEntityRepository.deleteById(id);
    }
}
