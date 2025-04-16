package io.spring.oauth2.jwt.cookie;

import org.springframework.http.ResponseCookie;

/**
 * @since alpha 0.0.1
 * @see CookieServiceImpl
 */
public interface CookieService {
    ResponseCookie generateRefreshTokenCookie(String refreshToken);
    ResponseCookie getCleanRefreshTokenCookie();
}