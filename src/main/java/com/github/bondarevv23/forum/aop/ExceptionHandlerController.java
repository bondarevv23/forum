package com.github.bondarevv23.forum.aop;

import com.github.bondarevv23.forum.domain.generated.ErrorResponse;
import com.github.bondarevv23.forum.domain.generated.NotFoundResponse;
import com.github.bondarevv23.forum.exception.BaseForumException;
import com.github.bondarevv23.forum.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::toString).toList();
        ErrorResponse response = getErrorResponse("invalid request argument", errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<NotFoundResponse> notFoundExceptionHandler(NotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({BaseForumException.class})
    public ResponseEntity<ErrorResponse> baseForumExceptionHandler(BaseForumException ex) {
        return ResponseEntity.status(ex.getCode()).body(getErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> uncaughtExceptionHandler() {
        return ResponseEntity.internalServerError().build();
    }

    private ErrorResponse getErrorResponse(String message, List<String> errors) {
        return ErrorResponse.builder()
                .errorMessage(message)
                .errors(errors)
                .build();
    }

    private ErrorResponse getErrorResponse(String message) {
        return getErrorResponse(message, Collections.emptyList());
    }
}
