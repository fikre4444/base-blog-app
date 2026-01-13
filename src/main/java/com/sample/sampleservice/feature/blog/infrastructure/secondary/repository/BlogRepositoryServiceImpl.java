package com.sample.sampleservice.feature.blog.infrastructure.secondary.repository;

import com.sample.sampleservice.feature.blog.domain.model.Blog;
import com.sample.sampleservice.feature.blog.domain.repository.BlogRepository;
import com.sample.sampleservice.feature.blog.infrastructure.secondary.domain.BlogEntity;
import com.sample.sampleservice.feature.blog.infrastructure.secondary.mapper.BlogEntityMapper;
import com.sample.sampleservice.shared.pagination.domain.Page;
import com.sample.sampleservice.shared.pagination.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BlogRepositoryServiceImpl implements BlogRepository {

    private final BlogEntityRepository blogEntityRepository;
    private final BlogEntityMapper blogEntityMapper;

    @Override
    public Blog save(Blog blog) {
        BlogEntity entity = blogEntityMapper.toEntity(blog);
        BlogEntity savedEntity = blogEntityRepository.save(entity);
        return blogEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Blog> findById(String id) {
        return blogEntityRepository.findById(id).map(blogEntityMapper::toDomain);
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest springPageable = PageRequest.of(pageable.getPage() - 1, pageable.getPageSize(), sort);

        org.springframework.data.domain.Page<BlogEntity> result = blogEntityRepository.findAll(springPageable);

        return new Page<Blog>()
                .content(result.getContent().stream().map(blogEntityMapper::toDomain).toList())
                .currentPage(result.getNumber() + 1)
                .total((int) result.getTotalElements())
                .totalPages(result.getTotalPages())
                .hasNext(result.hasNext())
                .hasPrevious(result.hasPrevious())
                .isLast(result.isLast());
    }

    @Override
    public void deleteById(String id) {
        blogEntityRepository.deleteById(id);
    }
}
