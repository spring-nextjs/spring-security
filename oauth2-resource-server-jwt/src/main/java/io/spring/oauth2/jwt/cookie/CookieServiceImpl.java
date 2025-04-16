package io.spring.oauth2.jwt.cookie;

import io.spring.oauth2.jwt.common.constant.CookieConstant;
import io.spring.oauth2.jwt.config.JwtConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

/**
 * This class implements the {@link CookieService} interface.
 * It generates and retrieves cookies for the refresh token.
 * The cookies are used to store the tokens in the client's browser.
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link JwtConfig}: Configuration for the JWT tokens.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Service}: Marks this class as a Spring service.</li>
 * </ul>
 *
 * @since alpha 0.0.1
 */
@Service
public record CookieServiceImpl(
        JwtConfig jwtConfig,
        @Value("${server.servlet.context-path}") String contextPath
) implements CookieService {

    @Override
    public ResponseCookie generateRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(CookieConstant.Header.REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .secure(true) // true in production
                .sameSite("Strict")
                .path(contextPath)
                .maxAge(jwtConfig.getRefreshExpiration())
                .build();
    }

    @Override
    public ResponseCookie getCleanRefreshTokenCookie() {
        return ResponseCookie
                .from(CookieConstant.Header.REFRESH_TOKEN, "")
                .httpOnly(true)
                .path(contextPath)
                .maxAge(0)
                .build();
    }
}