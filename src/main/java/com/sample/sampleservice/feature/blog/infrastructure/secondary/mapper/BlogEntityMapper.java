package com.sample.sampleservice.feature.blog.infrastructure.secondary.mapper;

import com.sample.sampleservice.feature.blog.domain.model.Blog;
import com.sample.sampleservice.feature.blog.infrastructure.secondary.domain.BlogEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlogEntityMapper {
    Blog toDomain(BlogEntity entity);

    BlogEntity toEntity(Blog domain);
}
