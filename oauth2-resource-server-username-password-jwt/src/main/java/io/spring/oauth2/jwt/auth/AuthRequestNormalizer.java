package io.spring.oauth2.jwt.auth;

import io.spring.oauth2.jwt.user.UserCreateDto;

/**
 * Utility class to normalize the fields of the authentication request objects.
 *
 * @since alpha 0.0.1
 */
class AuthRequestNormalizer {

    private AuthRequestNormalizer() {
        // Private constructor to prevent instantiation
    }

    public static void normalize(AuthLoginDto loginDto) {
        if (loginDto.getEmailOrUsername() != null) loginDto.setEmailOrUsername(loginDto.getEmailOrUsername().toLowerCase());
    }

    public static void normalize(UserCreateDto userCreateDto) {
        if (userCreateDto.getEmail() != null) userCreateDto.setEmail(userCreateDto.getEmail().toLowerCase());
        if (userCreateDto.getUsername() != null) userCreateDto.setUsername(userCreateDto.getUsername().toLowerCase());
    }
}