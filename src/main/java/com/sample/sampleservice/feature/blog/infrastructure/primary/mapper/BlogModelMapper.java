package com.sample.sampleservice.feature.blog.infrastructure.primary.mapper;

import com.sample.sampleservice.feature.blog.api.rest.v1.model.BlogDetail;
import com.sample.sampleservice.feature.blog.api.rest.v1.model.BlogRequest;
import com.sample.sampleservice.feature.blog.domain.model.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BlogModelMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    BlogDetail toDto(Blog blog);

    List<BlogDetail> toDto(List<Blog> blogs);

    default OffsetDateTime map(ZonedDateTime value) {
        return value == null ? null : value.toOffsetDateTime();
    }
}
