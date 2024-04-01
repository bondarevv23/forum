package com.github.bondarevv23.forum.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends BaseForumException {
    private final Long id;

    public NotFoundException(Long id, String message) {
        super(HttpStatus.NOT_FOUND, message);
        this.id = id;
    }
}
