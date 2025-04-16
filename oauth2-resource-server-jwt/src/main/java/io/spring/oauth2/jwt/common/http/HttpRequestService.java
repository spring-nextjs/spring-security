package io.spring.oauth2.jwt.common.http;

/**
 * @since alpha 0.0.1
 * @see HttpRequestServiceImpl
 */
public interface HttpRequestService {
    String getRequestUri();
    String extractIpFromXffHeader();
    String extractAccessTokenFromAuthorizationHeader();
    String extractRefreshTokenFromCookie();
}