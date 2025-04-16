package io.spring.oauth2.jwt.common.payload.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private final String message;
    private final String[] args;
}