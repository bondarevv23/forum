package com.github.bondarevv23.forum.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BaseForumException extends RuntimeException {
    private final HttpStatus code;
    private final String message;
}
