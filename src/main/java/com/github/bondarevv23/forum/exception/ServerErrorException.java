package com.github.bondarevv23.forum.exception;

import org.springframework.http.HttpStatus;

public class ServerErrorException extends BaseForumException {
    public ServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
