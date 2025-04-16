package io.spring.oauth2.jwt.auth;

record AuthValidateDto(
        String accessToken,
        String refreshToken
) {
}