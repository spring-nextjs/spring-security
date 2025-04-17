package io.spring.oauth2.jwt.auth;

import io.spring.oauth2.jwt.common.http.HttpRequestService;
import io.spring.oauth2.jwt.common.i18n.I18nService;
import io.spring.oauth2.jwt.jwt.JwtService;
import io.spring.oauth2.jwt.token.TokenService;
import io.spring.oauth2.jwt.user.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/**
 * Factory class that creates Authentication related instances.
 *
 * @since alpha 0.0.1
 */
@Component
record AuthFactory(
        HttpRequestService httpRequestService,
        I18nService i18nService,
        JwtService jwtService,
        TokenService tokenService
) {

    /**
     * Creates an {@link AuthDto} object with the provided user details, message, JWT, and refresh token.
     *
     * @param user the user for whom the authentication response is created
     * @param message the message key to be used for the response detail
     * @param accessToken the access token to be included in the response
     * @param refreshToken the refresh token to be included in the response
     * @return an {@link AuthDto} object containing the user details, message, JWT, and refresh token
     */
    AuthDto createAuthResponse(@NotNull User user, String message, String accessToken, String refreshToken) {
        AuthDto authDto = new AuthDto();
        authDto.setDetail(i18nService.getMessage(message, user.getUsername()));
        authDto.setInstance(httpRequestService != null ? httpRequestService.getRequestUri() : null);
        authDto.setAccessToken(accessToken);
        authDto.setRefreshToken(refreshToken);
        return authDto;
    }
}