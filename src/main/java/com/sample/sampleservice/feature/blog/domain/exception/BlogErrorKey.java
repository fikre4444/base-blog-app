package com.sample.sampleservice.feature.blog.domain.exception;

import com.sample.sampleservice.shared.error.domain.ErrorKey;

public enum BlogErrorKey implements ErrorKey {
    BLOG_NOT_FOUND("blog.not.found"),
    BLOG_UNAUTHORIZED("blog.unauthorized");

    private final String key;

    BlogErrorKey(String key) {
        this.key = key;
    }

    @Override
    public String get() {
        return key;
    }
}
