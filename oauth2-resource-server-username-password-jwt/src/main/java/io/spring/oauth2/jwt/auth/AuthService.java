package io.spring.oauth2.jwt.auth;

import io.spring.oauth2.jwt.user.UserCreateDto;

/**
 * @since alpha 0.0.1
 * @see AuthServiceImpl
 */
interface AuthService {
    AuthDto login(AuthLoginDto loginDto);
    AuthDto register(UserCreateDto userCreateDto);
    AuthDto refresh();
}