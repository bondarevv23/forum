package com.github.bondarevv23.forum.exception;

import java.util.function.Supplier;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super(id, String.format("user with id '%d' doesn't exist", id));
    }

    public static Supplier<UserNotFoundException> withId(Long id) {
        return () -> new UserNotFoundException(id);
    }
}
