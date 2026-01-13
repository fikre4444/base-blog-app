package com.sample.sampleservice.feature.blog.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class Blog {
    private String id;
    private String title;
    private String description;
    private String userId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
