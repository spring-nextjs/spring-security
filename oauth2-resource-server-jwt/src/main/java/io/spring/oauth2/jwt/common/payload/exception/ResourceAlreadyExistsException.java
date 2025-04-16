package io.spring.oauth2.jwt.common.payload.exception;

public class ResourceAlreadyExistsException extends BaseException {
    public ResourceAlreadyExistsException(String message, String[] args) {
        super(message, args);
    }
}