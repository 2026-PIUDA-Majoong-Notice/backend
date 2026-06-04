package com.notice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends RuntimeException {

    private final HttpStatus status;
    private final String code;

    protected HttpException(HttpStatus status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public static class BadRequest extends HttpException {
        public BadRequest(String message) {
            super(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", message);
        }
    }

    public static class Unauthorized extends HttpException {
        public Unauthorized(String message) {
            super(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", message);
        }
    }

    public static class Conflict extends HttpException {
        public Conflict(String message) {
            super(HttpStatus.CONFLICT, "CONFLICT", message);
        }
    }

    public static class NotFound extends HttpException {
        public NotFound(String message) {
            super(HttpStatus.NOT_FOUND, "NOT_FOUND", message);
        }
    }
}