package io.spring.oauth2.jwt.common.payload.exception;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String message, String[] args) {
        super(message, args);
    }
}