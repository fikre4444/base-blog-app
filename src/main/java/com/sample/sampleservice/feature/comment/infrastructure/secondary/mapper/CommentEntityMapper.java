package com.sample.sampleservice.feature.comment.infrastructure.secondary.mapper;

import com.sample.sampleservice.feature.comment.domain.model.Comment;
import com.sample.sampleservice.feature.comment.infrastructure.secondary.domain.CommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentEntityMapper {
    Comment toDomain(CommentEntity entity);

    CommentEntity toEntity(Comment domain);
}
