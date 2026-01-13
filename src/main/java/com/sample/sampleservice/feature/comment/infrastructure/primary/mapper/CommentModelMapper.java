package com.sample.sampleservice.feature.comment.infrastructure.primary.mapper;

import com.sample.sampleservice.feature.comment.api.rest.v1.model.CommentDetail;
import com.sample.sampleservice.feature.comment.domain.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentModelMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "blogId", source = "blogId")
    @Mapping(target = "replyCommentId", source = "replyCommentId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    CommentDetail toDto(Comment comment);

    List<CommentDetail> toDto(List<Comment> comments);

    default OffsetDateTime map(ZonedDateTime value) {
        return value == null ? null : value.toOffsetDateTime();
    }
}
