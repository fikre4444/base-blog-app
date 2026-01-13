package com.sample.sampleservice.feature.blog.domain.repository;

import com.sample.sampleservice.feature.blog.domain.model.Blog;
import com.sample.sampleservice.shared.pagination.domain.Page;
import com.sample.sampleservice.shared.pagination.domain.Pageable;

import java.util.Optional;

public interface BlogRepository {
    Blog save(Blog blog);

    Optional<Blog> findById(String id);

    Page<Blog> findAll(Pageable pageable);

    void deleteById(String id);
}
