package com.sample.sampleservice.feature.blog.infrastructure.secondary.repository;

import com.sample.sampleservice.feature.blog.infrastructure.secondary.domain.BlogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogEntityRepository extends JpaRepository<BlogEntity, String>, JpaSpecificationExecutor<BlogEntity> {
    Page<BlogEntity> findAllByUserId(String userId, Pageable pageable);
}
