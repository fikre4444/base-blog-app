package com.sample.sampleservice.feature.blog.application;

import com.sample.sampleservice.feature.blog.domain.exception.BlogErrorKey;
import com.sample.sampleservice.feature.blog.domain.model.Blog;
import com.sample.sampleservice.feature.blog.domain.repository.BlogRepository;
import com.sample.sampleservice.shared.authentication.application.AuthenticatedUser;
import com.sample.sampleservice.shared.error.domain.GeneratorException;
import com.sample.sampleservice.shared.pagination.domain.Page;
import com.sample.sampleservice.shared.pagination.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class BlogApplicationService {

    private final BlogRepository blogRepository;

    @Transactional
    public Blog createBlog(String title, String description) {
        String userId = AuthenticatedUser.getUser().id();

        Blog blog = Blog.builder()
                .title(title)
                .description(description)
                .userId(userId)
                .createdAt(ZonedDateTime.now())
                .build();

        return blogRepository.save(blog);
    }

    public Page<Blog> getBlogs(Pageable pageable) {
        String userId = AuthenticatedUser.getUser().id();
        return blogRepository.findByUserId(userId, pageable);
    }

    public Page<Blog> getBlogsByUserId(String userId, Pageable pageable) {
        return blogRepository.findByUserId(userId, pageable);
    }

    public Blog getBlog(String id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> GeneratorException.badRequest(BlogErrorKey.BLOG_NOT_FOUND).build());
    }

    @Transactional
    public Blog updateBlog(String id, String title, String description) {
        Blog existingBlog = getBlog(id);
        String currentUserId = AuthenticatedUser.getUser().id();

        if (!existingBlog.getUserId().equals(currentUserId)) {
            throw GeneratorException.badRequest(BlogErrorKey.BLOG_UNAUTHORIZED)
                    .message("You cannot update someone else's blog").build();
        }

        Blog updatedBlog = Blog.builder()
                .id(existingBlog.getId())
                .title(title)
                .description(description)
                .userId(existingBlog.getUserId())
                .createdAt(existingBlog.getCreatedAt())
                .updatedAt(ZonedDateTime.now())
                .build();

        return blogRepository.save(updatedBlog);
    }

    @Transactional
    public void deleteBlog(String id) {
        Blog existingBlog = getBlog(id);
        String currentUserId = AuthenticatedUser.getUser().id();

        if (!existingBlog.getUserId().equals(currentUserId)) {
            throw GeneratorException.badRequest(BlogErrorKey.BLOG_UNAUTHORIZED)
                    .message("You cannot delete someone else's blog").build();
        }

        blogRepository.deleteById(id);
    }
}
