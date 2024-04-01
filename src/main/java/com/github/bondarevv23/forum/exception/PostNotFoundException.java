package com.github.bondarevv23.forum.exception;

import java.util.function.Supplier;

public class PostNotFoundException extends NotFoundException {
    public PostNotFoundException(Long id) {
        super(id, String.format("post with id '%d' doesn't exist", id));
    }

    public static Supplier<PostNotFoundException> withId(Long id) {
        return () -> new PostNotFoundException(id);
    }
}
