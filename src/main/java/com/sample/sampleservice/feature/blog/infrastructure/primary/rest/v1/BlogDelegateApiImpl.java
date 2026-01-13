package com.sample.sampleservice.feature.blog.infrastructure.primary.rest.v1;

import com.sample.sampleservice.feature.blog.api.rest.v1.BlogsApiDelegate;
import com.sample.sampleservice.feature.blog.api.rest.v1.model.BlogDetail;
import com.sample.sampleservice.feature.blog.api.rest.v1.model.BlogDetailPaginated;
import com.sample.sampleservice.feature.blog.api.rest.v1.model.BlogRequest;
import com.sample.sampleservice.feature.blog.application.BlogApplicationService;
import com.sample.sampleservice.feature.blog.domain.model.Blog;
import com.sample.sampleservice.feature.blog.infrastructure.primary.mapper.BlogModelMapper;
import com.sample.sampleservice.shared.pagination.domain.Page;
import com.sample.sampleservice.shared.pagination.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BlogDelegateApiImpl implements BlogsApiDelegate {

    private final BlogApplicationService blogApplicationService;
    private final BlogModelMapper blogModelMapper;

    @Override
    public ResponseEntity<BlogDetail> createBlog(BlogRequest blogRequest) {
        Blog blog = blogApplicationService.createBlog(blogRequest.getTitle(), blogRequest.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(blogModelMapper.toDto(blog));
    }

    @Override
    public ResponseEntity<BlogDetailPaginated> getAllBlogs(Optional<Integer> page, Optional<Integer> limit) {
        Pageable pageable = Pageable.builder()
                .page(page.orElse(1))
                .pageSize(limit.orElse(20))
                .build();

        Page<Blog> blogs = blogApplicationService.getBlogs(pageable);

        BlogDetailPaginated response = new BlogDetailPaginated()
                .contents(blogModelMapper.toDto(blogs.getContent()))
                .total(blogs.getTotal())
                .totalPages(blogs.getTotalPages())
                .currentPage((int) blogs.getCurrentPage())
                .hasNext(blogs.isHasNext())
                .hasPrevious(blogs.isHasPrevious())
                .isLast(blogs.isLast());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BlogDetail> getBlogById(String id) {
        Blog blog = blogApplicationService.getBlog(id);
        return ResponseEntity.ok(blogModelMapper.toDto(blog));
    }

    @Override
    public ResponseEntity<BlogDetail> updateBlog(String id, BlogRequest blogRequest) {
        Blog blog = blogApplicationService.updateBlog(id, blogRequest.getTitle(), blogRequest.getDescription());
        return ResponseEntity.ok(blogModelMapper.toDto(blog));
    }

    @Override
    public ResponseEntity<Void> deleteBlog(String id) {
        blogApplicationService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }
}
