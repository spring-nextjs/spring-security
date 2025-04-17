package io.spring.oauth2.jwt.user;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email
) {
}